package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.fee.schedule.FeeScheduleEntity;
import com.cob.billing.model.bill.fee.schedule.FeeScheduleLineModel;
import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.repositories.bill.fee.schedule.FeeScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InvoiceFeeScheduleChargeUseCase {
    FeeScheduleEntity feeSchedule;
    @Autowired
    FeeScheduleRepository feeScheduleRepository;

    public void check(List<CPTCode> cptCode, Long insuranceId) {
        findFee(insuranceId);
        calculate(cptCode);
    }

    private void findFee(Long insuranceId) {
        Optional<FeeScheduleEntity> providerFeeSchedule = feeScheduleRepository.findFeeScheduleByInsurance(insuranceId);
        if (providerFeeSchedule.isPresent())
            feeSchedule = providerFeeSchedule.get();
        else
            feeSchedule = null;
    }

    private void calculate(List<CPTCode> cptCode) {
        if (feeSchedule != null) {
            cptCode.forEach(cpt -> {
                FeeScheduleLineModel scheduleLine = pickByCPTCode(cpt.getServiceCode());
                if (scheduleLine != null) {
                    switch (scheduleLine.getRateType()) {
                        case Per_Unit:
                            cpt.setCharge(cpt.getUnit() * scheduleLine.getChargeAmount());
                            break;
                        case Fixed:
                            cpt.setCharge(scheduleLine.getChargeAmount());
                            break;
                    }
                }
            });

        }
    }

    private FeeScheduleLineModel pickByCPTCode(String code) {

        Optional<FeeScheduleLineModel> result = feeSchedule.getFeeLines().stream()
                .filter(line -> line.getCptCode().equals(code))
                .findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }
}
