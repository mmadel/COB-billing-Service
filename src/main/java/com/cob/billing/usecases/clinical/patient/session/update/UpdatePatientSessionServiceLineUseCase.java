package com.cob.billing.usecases.clinical.patient.session.update;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdatePatientSessionServiceLineUseCase {
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    ModelMapper mapper;

    public void update(List<ServiceLine> models) {
        List<PatientSessionServiceLineEntity> lines = models.stream()
                .map(serviceLine -> mapper.map(serviceLine, PatientSessionServiceLineEntity.class)).collect(Collectors.toList());
        serviceLineRepository.saveAll(lines);

    }
}
