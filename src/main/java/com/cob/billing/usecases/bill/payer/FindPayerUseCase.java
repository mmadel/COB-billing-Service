package com.cob.billing.usecases.bill.payer;

import com.cob.billing.enums.PayerType;
import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.integration.claimmd.era.PayerClaimMD;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.usecases.integration.claim.md.RetrievePayerListUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindPayerUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    ModelMapper mapper;

    @Cacheable("payers")
    public List<Payer> find() {
        List<Payer> payers = new ArrayList<>();
//        insuranceCompanyRepository.findAll()
//                .stream()
//                .forEach(insuranceCompany -> {
//                    Payer payer = new Payer();
//                    payer.setName(insuranceCompany.getName());
//                    payer.setDisplayName(insuranceCompany.getName());
//                    payer.setAddress(insuranceCompany.getAddress());
//                    payer.setPayerType(PayerType.User_Defined);
//                    payers.add(payer);
//                });
        repository.findAll().stream()
                .forEach(payerEntity -> {
                    Payer payer = mapper.map(payerEntity, Payer.class);
                    payer.setPayerType(PayerType.Clearing_House);
                    payers.add(payer);
                });
        return payers;
    }
}
