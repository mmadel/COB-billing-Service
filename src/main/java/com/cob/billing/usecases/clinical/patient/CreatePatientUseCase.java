package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.referring.provider.ReferringProviderEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.ReferringProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePatientUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientRepository repository;

    @Autowired
    ReferringProviderRepository referringProviderRepository;

    @Transactional
    public Long create(Patient patient) {

        PatientEntity toBeCreated = mapper.map(patient, PatientEntity.class);
        toBeCreated.setAuthorizationWatching(true);
        toBeCreated.setReferringProvider(null);
        PatientEntity created = repository.save(toBeCreated);

        if (patient.getReferringProvider() != null && !patient.getReferringProvider().isEmpty())
            assignReferringProvider(created, patient.getReferringProvider().getNpi());
        return created.getId();
    }

    private void assignReferringProvider(PatientEntity patient, String npi) {
        ReferringProviderEntity referringProvider = referringProviderRepository.findByNpi(npi).orElseThrow(() -> new IllegalArgumentException("referring provider not found"));
        patient.setReferringProvider(referringProvider);
        repository.save(patient);
    }


}
