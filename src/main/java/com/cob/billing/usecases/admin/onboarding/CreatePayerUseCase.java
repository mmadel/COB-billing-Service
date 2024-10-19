package com.cob.billing.usecases.admin.onboarding;

import com.cob.billing.entity.bill.payer.PayerEntity;
import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.model.integration.claimmd.era.PayerClaimMD;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.usecases.integration.claim.md.RetrievePayerListUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreatePayerUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    RetrievePayerListUseCase retrievePayerListUseCase;

    public void create() throws OrganizationException {
        try {
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
        } catch (Exception exception) {
            throw new OrganizationException(HttpStatus.CONFLICT, "", new Object[]{});
        }
    }
}
