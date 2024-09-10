package com.cob.billing.usecases.bill.payer;

import com.cob.billing.entity.bill.payer.PayerEntity;
import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.common.BasicAddress;
import com.cob.billing.model.integration.claimmd.era.PayerClaimMD;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.usecases.integration.claim.md.RetrievePayerListUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddPayerUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    RetrievePayerListUseCase retrievePayerListUseCase;

    public void add(List<Payer> models) {
        List<PayerEntity> entities = models.stream()
                .map(payer -> mapper.map(payer, PayerEntity.class))
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }
    public void add(){
        List<PayerEntity> entities = new ArrayList<>();
        List<PayerClaimMD> payersClaimMD = retrievePayerListUseCase.getList().getPayer();
        payersClaimMD.stream()
                .forEach(payerClaimMD -> {
                    PayerEntity payerEntity = new PayerEntity();
                    payerEntity.setPayerId(payerClaimMD.getPayerid());
                    payerEntity.setName(payerClaimMD.payer_name);
                    payerEntity.setDisplayName(payerClaimMD.payer_name.trim());
                    entities.add(payerEntity);

                });
        repository.saveAll(entities);
    }
}
