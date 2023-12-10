package com.cob.billing.usecases.bill.payer;

import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPayerUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;
    public List<Payer> find(){
        return repository.findAll().stream()
                .map(payerEntity -> mapper.map(payerEntity, Payer.class))
                .collect(Collectors.toList());
    }
}
