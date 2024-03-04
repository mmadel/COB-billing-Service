package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.PatientSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.model.response.PatientSessionServiceLineResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindNotSubmittedSessionsByPatientUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public PatientSessionServiceLineResponse find(int offset, int limit, Long patientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findPrepareAndPartialSessionsByPatient(patientId);
        return createPatientSessionServiceLineResponse(offset, limit, patientSessionEntities);
    }

    public PatientSessionServiceLineResponse findFiltered(int offset, int limit, Long patientId, PatientSessionSearchCriteria patientSessionSearchCriteria) {

        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findPrepareAndPartialSessionsByPatientFilteredByDate(patientId
                , patientSessionSearchCriteria.getStartDate(), patientSessionSearchCriteria.getEndDate()
                , patientSessionSearchCriteria.getProvider() != null ? patientSessionSearchCriteria.getProvider().toUpperCase().trim() : null,
                patientSessionSearchCriteria.getSessionCase() != null ? patientSessionSearchCriteria.getSessionCase().toUpperCase().trim() : null);
        return createPatientSessionServiceLineResponse(offset, limit, patientSessionEntities);
    }

    private PatientSessionServiceLineResponse createPatientSessionServiceLineResponse(int offset, int limit, List<PatientSessionEntity> patientSessionEntities) {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(destination.getPatientName());
            }
        });

        List<PatientSession> response = patientSessionEntities.stream()
                .map(patientSessionEntity -> {
                    PatientSession patientSession = mapper.map(patientSessionEntity, PatientSession.class);
                    patientSession.setPatientName(patientSessionEntity.getPatient().getLastName() + ',' + patientSessionEntity.getPatient().getFirstName());
                    return patientSession;
                }).collect(Collectors.toList());
        List<PatientSessionServiceLine> patientSessionServiceLines = constructPatientSessionServiceLines(response);
        List<PatientSessionServiceLine> records = PaginationUtil.paginate(patientSessionServiceLines, offset, limit);

        return PatientSessionServiceLineResponse.builder()
                .number_of_records(patientSessionServiceLines.size())
                .number_of_matching_records(records.size())
                .records(records)
                .build();
    }

    private List<PatientSessionServiceLine> constructPatientSessionServiceLines(List<PatientSession> sessions) {
        List<PatientSessionServiceLine> lines = new ArrayList<>();
        for (int i = 0; i < sessions.size(); i++) {
            PatientSession session = sessions.get(i);
            for (int j = 0; j < session.getServiceCodes().size(); j++) {
                ServiceLine serviceCode = session.getServiceCodes().get(j);
                lines.add(
                        PatientSessionServiceLine.builder()
                                .dos(session.getServiceDate())
                                .provider(session.getDoctorInfo().getDoctorLastName() + ',' + session.getDoctorInfo().getDoctorFirstName())
                                .caseTitle(session.getCaseTitle())
                                .place(session.getPlaceOfCode().split("_")[0])
                                .cpt(serviceCode.getCptCode().getServiceCode())
                                .unit(serviceCode.getCptCode().getUnit())
                                .charge(serviceCode.getCptCode().getCharge())
                                .cptId(serviceCode.getId())
                                .serviceCode(serviceCode)
                                .isCorrect(serviceCode.getIsCorrect())
                                .data(session)
                                .build()
                );
            }
        }
        return lines;
    }
}
