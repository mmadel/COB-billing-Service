package com.cob.billing.usecases.clinical.patient;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.bill.posting.ClientSearchResult;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.repositories.clinical.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindPatientByNamUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    MapPatientUseCase mapPatientUseCase;

    public List<ClientSearchResult> find(String name) {
        List<ClientSearchResult> results = new ArrayList<>();
        patientRepository.findByName(name.trim().toLowerCase()).stream()
                .forEach(entity -> {
                    ClientSearchResult searchResult = new ClientSearchResult();
                    searchResult.setClientId(entity.getId());
                    searchResult.setClientName(entity.getFirstName() + "," + entity.getLastName());
                    results.add(searchResult);
                });
        return results;
    }

    public Patient findByFirstAndLastName(String firstName, String lastName) {
        PatientEntity patientEntity = patientRepository.findByFullName(firstName.toLowerCase(), lastName.toLowerCase());
        return mapPatientUseCase.map(patientEntity);
    }
}
