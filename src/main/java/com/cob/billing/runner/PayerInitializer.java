package com.cob.billing.runner;

import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.usecases.bill.payer.AddPayerUseCase;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PayerInitializer implements CommandLineRunner {
    @Autowired
    PayerRepository payerRepository;
    @Autowired
    AddPayerUseCase addPayerUseCase;
    @Override
    public void run(String... args) {
        boolean hasPayers = payerRepository.existsBy();
        if(!hasPayers){
            System.out.println("Add Payers...");
            addPayerUseCase.add();
        }
    }
}
