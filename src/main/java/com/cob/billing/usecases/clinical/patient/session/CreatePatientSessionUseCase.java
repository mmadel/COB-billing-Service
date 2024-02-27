package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.PatientCaseEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.provider.ProviderEntity;
import com.cob.billing.enums.PatientSessionStatus;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.repositories.clinical.PatientCaseRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.ProviderRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreatePatientSessionUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    ProviderRepository providerRepository;

    public Long create(PatientSession model) {
        PatientEntity patient = patientRepository.findById(model.getPatientId()).get();
        PatientSessionEntity toBeCreated = mapper.map(model, PatientSessionEntity.class);
        toBeCreated.setPatient(patient);
        toBeCreated.setStatus(PatientSessionStatus.Prepare);
        if (!model.getIsCasesAttached())
            createPatientCase(patient, toBeCreated);
        ProviderEntity providerEntity = providerRepository.findById(model.getDoctorInfo().getDoctorId()).get();
        toBeCreated.getDoctorInfo().setLegacyID(providerEntity.getLegacyID());

        toBeCreated.getServiceCodes().forEach(patientSessionServiceLineEntity -> {
            if (patientSessionServiceLineEntity.getIsCorrect() == null)
                patientSessionServiceLineEntity.setIsCorrect(false);
        });
        return patientSessionRepository.save(toBeCreated).getId();
    }

    private void createPatientCase(PatientEntity patient, PatientSessionEntity patientSession) {
        PatientCaseEntity patientCaseEntity = new PatientCaseEntity();
        patientCaseEntity.setPatient(patient);
        patientCaseEntity.setCaseDiagnosis(patientSession.getCaseDiagnosis());
        patientCaseEntity.setTitle(patientSession.getCaseTitle());
        patientCaseRepository.save(patientCaseEntity);
    }
}
