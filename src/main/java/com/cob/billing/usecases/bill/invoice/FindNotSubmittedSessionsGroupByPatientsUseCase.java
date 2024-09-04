package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.invoice.history.PatientSessionHistory;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.response.PatientSessionHistoryResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
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

    public PatientSessionHistoryResponse find(int offset, int limit) {
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
        List<PatientSessionHistory> response = new ArrayList<>();
        result.forEach((patient, patientSession) -> {
            PatientSessionHistory patientSessionHistory = new PatientSessionHistory();
            patientSessionHistory.setPatientId(patient);
            patientSessionHistory.setPatientName(patientSession.get(0).getPatientName());
            patientSessionHistory.setPatientSession(patientSession);
            response.add(patientSessionHistory);
        });
        List<PatientSessionHistory> records = PaginationUtil.
                paginate(response, offset, limit);
        return PatientSessionHistoryResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) response.size())
                .records(records)
                .build();
    }

}
