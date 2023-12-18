package com.cob.billing.usecases.bill.insurance.company;

import com.cob.billing.model.bill.posting.ClientSearchResult;
import com.cob.billing.model.bill.posting.InsuranceCompanySearchResult;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindInsuranceCompanyUseCase {
    @Autowired
    PayerRepository repository;
    @Autowired
    ModelMapper mapper;

    public List<InsuranceCompanySearchResult> find(String name) {
        List<InsuranceCompanySearchResult> results = new ArrayList<>();
        repository.findByName(name)
                .forEach(entity -> {
                    InsuranceCompanySearchResult searchResult = new InsuranceCompanySearchResult();
                    searchResult.setId(entity.getId());
                    searchResult.setPayerId(entity.getPayerId());
                    searchResult.setPayerName(entity.getName());
                    searchResult.setDisplayName(entity.getDisplayName());
                    results.add(searchResult);
                });
        return results;
    }
}
