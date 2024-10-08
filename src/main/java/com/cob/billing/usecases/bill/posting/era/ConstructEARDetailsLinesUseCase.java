package com.cob.billing.usecases.bill.posting.era;

import com.cob.billing.model.bill.posting.era.ERALineTransferModel;
import com.cob.billing.model.integration.claimmd.era.Adjustment;
import com.cob.billing.model.integration.claimmd.era.Charge;
import com.cob.billing.model.integration.claimmd.era.Claim;
import com.cob.billing.usecases.bill.era.FindClaimStatusCodeUseCase;
import com.cob.billing.util.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConstructEARDetailsLinesUseCase {
    public List<ERALineTransferModel> constructLines(List<Claim> claims) {
        List<ERALineTransferModel> lines = new ArrayList<>();
        claims
                .stream().forEach(claim -> {
                    for (Charge charge : claim.getCharge()) {
                        ERALineTransferModel lineTransferModel = new ERALineTransferModel();
                        lineTransferModel.setAllowedAmount(Double.parseDouble(charge.getAllowed()));
                        double[] amounts = getAmounts(charge.getAdjustment(), Double.parseDouble(charge.getCharge()), Double.parseDouble(charge.getPaid()));
                        lineTransferModel.setBillAmount(Double.parseDouble(charge.getCharge()));
                        lineTransferModel.setPaidAmount(Double.parseDouble(charge.getPaid()));
                        lineTransferModel.setDeductAmount(amounts[0]);
                        lineTransferModel.setCoInsuranceAmount(amounts[1]);
                        lineTransferModel.setCoPaymentAmount(amounts[2]);
                        lineTransferModel.setAdjustAmount(amounts[3]);
                        lineTransferModel.setCptCode(Long.parseLong(charge.getProc_code()));
                        lineTransferModel.setUnits(Integer.parseInt(charge.getUnits()));
                        lineTransferModel.setDos(charge.getFrom_dos());
                        lineTransferModel.setReasons(getReasons(charge.getAdjustment()).toArray(new String[charge.getAdjustment().size()]));
                        lineTransferModel.setServiceLineID(Integer.parseInt(charge.getChgid()));
                        lineTransferModel.setPatientName(claim.getPat_name_l() + "," + claim.getPat_name_f());
                        lineTransferModel.setAction("Close");
                        lineTransferModel.setClaimStatusCode(claim.getStatus_code());
                        lineTransferModel.setClaimStatusDescription(getClaimDescription(claim.getStatus_code()));
                        lineTransferModel.setSelected(true);
                        lineTransferModel.setEditAdjustAmount(lineTransferModel.getAdjustAmount());
                        lineTransferModel.setEditPaidAmount(lineTransferModel.getPaidAmount());
                        lines.add(lineTransferModel);
                    }

                });
        return lines;
    }

    private double[] getAmounts(List<Adjustment> adjustments, double charge, double paid) {
        double deduct = 0.0;
        double coInsurance = 0.0;
        double coPayment = 0.0;
        double adjust = 0.0;
        for (Adjustment adjustment : adjustments) {
            switch (adjustment.getCode()) {
                case "1":
                    deduct = Double.parseDouble(adjustment.getAmount());
                    break;
                case "2":
                    coInsurance = Double.parseDouble(adjustment.getAmount());
                    break;
                case "3":
                    coPayment = Double.parseDouble(adjustment.getAmount());
                    break;
            }
        }
        double value = charge - (paid + (deduct + coInsurance + coPayment));
        adjust = Double.parseDouble(String.format("%.2f", value));
        /*
            Result
                [0] deduct amount
                [1] coInsurance
                [2] coPayment
                [3] adjust
         */
        return new double[]{deduct, coInsurance, coPayment, adjust};
    }

    private String getClaimDescription(String statusCode) {
        FindClaimStatusCodeUseCase findClaimStatusCodeUseCase = BeanFactory.getBean(FindClaimStatusCodeUseCase.class);
        return findClaimStatusCodeUseCase.find(statusCode);
    }

    private List<String> getReasons(List<Adjustment> adjustments) {
        List<String> reasons = new ArrayList<>();
        for (Adjustment adjustment : adjustments) {
            String reason = adjustment.getCode();
            reasons.add(reason);
        }
        return reasons;
    }
}
