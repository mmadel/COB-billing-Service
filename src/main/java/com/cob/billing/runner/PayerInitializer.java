package com.cob.billing.runner;

import com.cob.billing.repositories.bill.payer.PayerRepository;
import com.cob.billing.usecases.bill.payer.AddPayerUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayerInitializer implements CommandLineRunner {
    @Autowired
    PayerRepository payerRepository;
    @Autowired
    AddPayerUseCase addPayerUseCase;

    @Override
    public void run(String... args) {
        boolean hasPayers = payerRepository.existsBy();
        if (!hasPayers) {
            log.info("Payers have been added to cache.");
            addPayerUseCase.add();
        }
    }
}
