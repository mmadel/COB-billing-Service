package com.cob.billing.usecases.bill;

import com.cob.billing.entity.bill.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.model.bill.InsuranceCompanyContainer;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.payer.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class FindInsuranceCompanyListUseCase {
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    PayerRepository payerRepository;

    public List<InsuranceCompanyContainer> find() {
        List<InsuranceCompanyContainer> insuranceCompanyContainerList = new ArrayList<>();
        List<InsuranceCompanyEntity> entities = insuranceCompanyRepository.findAll();
        List<Long> distinctPayerIds = new ArrayList<>();
        List<InsuranceCompanyEntity> toAssigned = new ArrayList<>();
        IntStream.range(1, entities.size())
                .forEach(i -> {
                    InsuranceCompanyEntity currentElement = entities.get(i);
                    InsuranceCompanyEntity previousElement = entities.get(i - 1);
                    if (currentElement.getPayerId() == null)
                        toAssigned.add(currentElement);
                    else if (currentElement.getPayerId().equals(previousElement.getPayerId()))
                        distinctPayerIds.add(currentElement.getPayerId());
                    else if (currentElement.getPayerId() != null)
                        distinctPayerIds.add(currentElement.getPayerId());
                });
        payerRepository.findByListOfPayerIds(distinctPayerIds).stream()
                .forEach(payerEntity -> {
                    InsuranceCompanyContainer insuranceCompanyContainer = new InsuranceCompanyContainer();
                    insuranceCompanyContainer.setDisplayName(payerEntity.getName());
                    insuranceCompanyContainer.setPayerId(payerEntity.getPayerId());
                    insuranceCompanyContainerList.add(insuranceCompanyContainer);
                });
        toAssigned.stream()
                .forEach(insuranceCompanyEntity -> {
                    InsuranceCompanyContainer insuranceCompanyContainer = new InsuranceCompanyContainer();
                    insuranceCompanyContainer.setDisplayName(insuranceCompanyEntity.getName());
                    insuranceCompanyContainer.setInsuranceCompanyId(insuranceCompanyEntity.getId());
                    insuranceCompanyContainerList.add(insuranceCompanyContainer);
                });
        return insuranceCompanyContainerList;
    }
}
