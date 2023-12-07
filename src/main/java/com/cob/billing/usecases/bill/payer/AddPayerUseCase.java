package com.cob.billing.usecases.bill.payer;

import com.cob.billing.entity.bill.payer.PayerEntity;
import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddPayerUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;

    public void add(List<Payer> models) {
        List<PayerEntity> entities = models.stream()
                .map(payer -> mapper.map(payer, PayerEntity.class))
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }
}
