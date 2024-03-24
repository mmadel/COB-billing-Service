package com.cob.billing.usecases.bill.history;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceDetailsEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.PatientSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.history.SessionHistoryCount;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindSessionHistoryUseCase {
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;

    public SessionHistoryResponse find(Pageable paging) {
        List<SessionHistory> result = new ArrayList<>();
        Page<PatientInvoiceEntity> pages = patientInvoiceRepository.findAll(paging);
        List<PatientInvoiceEntity> invoiceEntities = pages.getContent();
        long total = (pages).getTotalElements();
        invoiceEntities.stream()
                .filter(patientInvoice -> patientInvoice.getSubmissionId() != null)
                .collect(Collectors.toList());
        // Submissions
        invoiceEntities.stream()
                .filter(patientInvoice -> patientInvoice.getInvoiceDetails().size() > 0)
                //Group By Sessions
                .forEach(patientInvoice -> {
                    Map<Long, List<PatientInvoiceDetailsEntity>> invoiceDetailsMapper = patientInvoice.getInvoiceDetails().stream()
                            .filter(patientInvoiceDetails -> patientInvoiceDetails.getPatientSession() != null)
                            .collect(Collectors.groupingBy(patientInvoiceDetails -> patientInvoiceDetails.getPatientSession().getId()));

                    patientInvoice.getInvoiceDetails().stream()
                            .filter(patientInvoiceDetails -> patientInvoiceDetails.getPatientSession() != null)
                            .filter(patientInvoiceDetails -> patientInvoiceDetails.getPatientSession().getDoctorInfo() != null)
                            .collect(Collectors.groupingBy(patientInvoiceDetails -> patientInvoiceDetails.getPatientSession().getDoctorInfo().getDoctorNPI()));
                    //Create Submission History Record
                    SessionHistory sessionHistory = new SessionHistory();
                    sessionHistory.setSubmissionType(SubmissionType.Print);
                    sessionHistory.setSubmitDate(patientInvoice.getCreatedAt());
                    sessionHistory.setSubmissionId(patientInvoice.getSubmissionId());
                    sessionHistory.setInsuranceCompany(patientInvoice.getInsuranceCompany().getName());
                    sessionHistory.setSubmissionStatus(SubmissionStatus.Success);
                    sessionHistory.setClient(mapPatient(patientInvoice.getPatient()));
                    List<SessionHistoryCount> counts = new ArrayList<>();
                    // Create Session Counter
                    for (Long submissionId : invoiceDetailsMapper.keySet()) {
                        List<PatientInvoiceDetailsEntity> lines = invoiceDetailsMapper.get(submissionId);
                        PatientInvoiceDetailsEntity lineDetail = lines.stream().findFirst().get();
                        SessionHistoryCount count = new SessionHistoryCount();
                        count.setSessionId(submissionId);
                        count.setDateOfService(lineDetail.getPatientSession().getServiceDate());
                        sessionHistory.setProvider(lineDetail.getPatientSession().getDoctorInfo().getDoctorLastName()
                                + ','
                                + lineDetail.getPatientSession().getDoctorInfo().getDoctorFirstName());
                        count.setServiceLines(lines.size());
                        count.setServiceLine(mapPatientSessionServiceLine((lines)));
                        counts.add(count);
                    }
                    sessionHistory.setSessionCounts(counts);
                    result.add(sessionHistory);
                });
        return SessionHistoryResponse.builder()
                .number_of_records((int)total)
                .number_of_matching_records((int) invoiceEntities.size())
                .records(result)
                .build();
    }
    private List<ServiceLine> mapPatientSessionServiceLine(List<PatientInvoiceDetailsEntity> detailsEntities){
        List<ServiceLine> serviceLines = new ArrayList<>();
        detailsEntities.forEach(patientInvoiceDetails -> {
            PatientSessionServiceLineEntity serviceLine = patientInvoiceDetails.getServiceLine();
            ServiceLine line = new ServiceLine();
            line.setType(serviceLine.getType());
            line.setCptCode(serviceLine.getCptCode());
            line.setDiagnoses(serviceLine.getDiagnoses());
            serviceLines.add(line);
        });
        return serviceLines;
    }
    private Patient mapPatient(PatientEntity entity){
        Patient patient = new Patient();
        patient.setId(entity.getId());
        patient.setBirthDate(entity.getBirthDate());
        patient.setFirstName(entity.getFirstName());
        patient.setLastName(entity.getLastName());
        return patient;
    }
}
