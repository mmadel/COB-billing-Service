package com.cob.billing.usecases.admin.clinic;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.repositories.admin.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public Long create(Clinic model) {
        ClinicEntity toBeCreated = mapper.map(model, ClinicEntity.class);
        return clinicRepository.save(toBeCreated).getId();
    }
}
