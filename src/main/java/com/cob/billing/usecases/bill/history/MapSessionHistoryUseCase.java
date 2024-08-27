package com.cob.billing.usecases.bill.history;

import com.cob.billing.entity.bill.invoice.PatientInvoiceDetailsEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.claim.PatientClaimEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.history.SessionHistoryCount;
import com.cob.billing.repositories.bill.invoice.PatientClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MapSessionHistoryUseCase {
    @Autowired
    PatientClaimRepository patientClaimRepository;

    public List<SessionHistory> map(List<PatientInvoiceEntity> invoiceEntities) {
        List<SessionHistory> result = new ArrayList<>();
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
                    sessionHistory.setSubmissionType(patientInvoice.getSubmissionType());
                    sessionHistory.setSubmitDate(patientInvoice.getCreatedAt());
                    sessionHistory.setSubmissionId(patientInvoice.getSubmissionId());
                    sessionHistory.setInsuranceCompany(patientInvoice.getInsuranceCompany().getName());
                    sessionHistory.setClient(mapPatient(patientInvoice.getPatient()));
                    sessionHistory.setSubmissionStatus(findStatus(patientInvoice.getId()));
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
        return result;
    }

    private Patient mapPatient(PatientEntity entity) {
        Patient patient = new Patient();
        patient.setId(entity.getId());
        patient.setBirthDate(entity.getBirthDate());
        patient.setFirstName(entity.getFirstName());
        patient.setLastName(entity.getLastName());
        return patient;
    }

    private List<ServiceLine> mapPatientSessionServiceLine(List<PatientInvoiceDetailsEntity> detailsEntities) {
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

    private SubmissionStatus findStatus(Long patientInvoiceId) {
        Optional<PatientClaimEntity> optionalPatientClaimEntity = patientClaimRepository.findDistinctByPatientInvoice_Id(patientInvoiceId);
        if (optionalPatientClaimEntity.isPresent()) {
            PatientClaimEntity patientClaim = optionalPatientClaimEntity.get();
            switch (patientClaim.getSubmissionStatus()) {
                case Claim_Acknowledgment:
                    return SubmissionStatus.Pending;
                case Claim_Rejection:
                    return SubmissionStatus.error;
                default:
                    return SubmissionStatus.Success;
            }
        }
        return null;
    }
}
