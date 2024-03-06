package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.invoice.tmp.InvoiceResponse;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.response.InvoicePatientSessionResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.clinical.patient.MapPatientUseCase;
import com.cob.billing.util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FindNotSubmittedSessionsGroupByPatientsUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public InvoicePatientSessionResponse find(int offset, int limit) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findPrepareAndPartialSessions();
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(destination.getPatientName());
            }
        });
        Map<Long, List<PatientSession>> result = patientSessionEntities.stream()
                .map(patientSessionEntity -> {
                    PatientSession patientSession = mapper.map(patientSessionEntity, PatientSession.class);
                    patientSession.setPatientName(patientSessionEntity.getPatient().getLastName() + ',' + patientSessionEntity.getPatient().getFirstName());
                    return patientSession;
                })
                .collect(Collectors.groupingBy(PatientSession::getPatientId));
        List<InvoiceResponse> response = new ArrayList<>();
        result.forEach((patient, patientSession) -> {
            InvoiceResponse invoiceResponse = new InvoiceResponse();
            invoiceResponse.setPatientId(patient);
            invoiceResponse.setPatientName(patientSession.get(0).getPatientName());
            invoiceResponse.setPatientSession(patientSession);
            response.add(invoiceResponse);
        });
        List<InvoiceResponse> records = PaginationUtil.paginate(response, offset, limit);
        return InvoicePatientSessionResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) response.size())
                .records(records)
                .build();
    }

}
