package com.cob.billing.usecases.admin.clinic;

import com.cob.billing.repositories.admin.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    public Long delete(Long clinicId){
        clinicRepository.deleteById(clinicId);
        return clinicId;
    }
}
