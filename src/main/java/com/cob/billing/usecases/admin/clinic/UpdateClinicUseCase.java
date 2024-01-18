package com.cob.billing.usecases.admin.clinic;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.repositories.admin.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public Long update(Clinic model) {
        ClinicEntity toBeUpdated = mapper.map(model, ClinicEntity.class);
        return clinicRepository.save(toBeUpdated).getId();
    }
}
