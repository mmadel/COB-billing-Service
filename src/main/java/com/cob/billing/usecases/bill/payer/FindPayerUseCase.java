package com.cob.billing.usecases.bill.payer;

import com.cob.billing.model.bill.payer.Payer;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyHolder;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindPayerUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    ModelMapper mapper;

    public List<Payer> find() {
        List<Payer> payers = new ArrayList<>();
        insuranceCompanyRepository.findAll()
                .stream()
                .forEach(insuranceCompany -> {
                    Payer payer = new Payer();
                    payer.setName(insuranceCompany.getName());
                    payer.setDisplayName(insuranceCompany.getName());
                    payer.setAddress(insuranceCompany.getAddress());
                    payers.add(payer);
                });
        repository.findAll().stream()
                .forEach(payerEntity -> {
                    payers.add(mapper.map(payerEntity, Payer.class));
                });
        return payers;
    }
}
