package com.cob.billing.usecases.admin.clinic;

import com.cob.billing.entity.admin.ClinicEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.response.ClinicResponse;
import com.cob.billing.model.response.PatientResponse;
import com.cob.billing.repositories.admin.ClinicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClinicUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public ClinicResponse findAll(Pageable paging){
        Page<ClinicEntity> pages = clinicRepository.findAll(paging);
        long total = (pages).getTotalElements();
        List<Clinic> records = pages.stream().map(clinic -> mapper.map(clinic, Clinic.class))
                .collect(Collectors.toList());
        return ClinicResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }
}
