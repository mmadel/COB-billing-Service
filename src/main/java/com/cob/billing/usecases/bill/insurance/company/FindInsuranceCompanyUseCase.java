package com.cob.billing.usecases.bill.insurance.company;

import com.cob.billing.model.bill.posting.InsuranceCompanySearchResult;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.usecases.bill.FindInsuranceCompanyListUseCase;
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
    @Autowired
    FindInsuranceCompanyListUseCase findInsuranceCompanyListUseCase;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    public List<InsuranceCompanySearchResult> find(String name) {
        List<InsuranceCompanySearchResult> results = new ArrayList<>();
        findInsuranceCompanyListUseCase.find().stream()
                .filter(insuranceCompanyContainer -> insuranceCompanyContainer.getDisplayName().contains(name))
                .forEach(entity -> {
                    InsuranceCompanySearchResult searchResult = new InsuranceCompanySearchResult();
                    if (entity.getPayerId() != null)
                        searchResult.setId(entity.getPayerId());
                    else {
                        searchResult.setId(insuranceCompanyRepository.findByInsuranceCompanyName(entity.getDisplayName()).getId());
                    }
                    searchResult.setDisplayName(entity.getDisplayName());
                    results.add(searchResult);
                });
        return results;
    }
}
