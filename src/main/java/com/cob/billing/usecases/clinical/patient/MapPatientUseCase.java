package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.insurance.PatientInsurance;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientInsuranceRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.bill.posting.FindSessionPaymentUseCase;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapPatientUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;
    @Autowired
    MapPatientInsurancesUseCase mapPatientInsurancesUseCase;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    FindSessionPaymentUseCase findSessionPaymentUseCase;
    @Autowired
    EnrichServiceLineWithPaymentUseCase enrichServiceLineWithPaymentUseCase;
    public Patient map(PatientEntity entity) {
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientEntity, Patient>() {
            @Override
            protected void configure() {
                skip(
                        destination.getPatientInsurances());
            }
        });
        Patient patient = mapper.map(entity, Patient.class);

        List<PatientInsuranceEntity> patientInsuranceEntities = patientInsuranceRepository.findByPatient_Id(entity.getId());
        List<PatientInsurance> patientInsurances = mapPatientInsurancesUseCase.map(patientInsuranceEntities);
        patient.setPatientInsurances(patientInsurances);
        patient.setSessions(findPatientSession(entity.getId()));
        return patient;
    }

    private List<PatientSession> findPatientSession(Long patientId) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findByPatient_Id(patientId).get();
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(
                        destination.getPatientName());
            }
        });
        List<PatientSession> sessions = patientSessionEntities.stream()
                .map(patientSessionEntity -> {
                    PatientSession session = mapper.map(patientSessionEntity, PatientSession.class);
                    enrichServiceLineWithPaymentUseCase.enrichPayment(session);
                    return session;
                })
                .collect(Collectors.toList());

        return sessions;
    }
}
