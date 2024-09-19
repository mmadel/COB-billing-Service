package com.cob.billing.usecases.bill.posting.era.mapper;

import com.cob.billing.model.bill.posting.era.ERADataDetailTransferModel;
import com.cob.billing.model.bill.posting.era.ERALineTransferModel;
import com.cob.billing.model.integration.claimmd.era.Adjustment;
import com.cob.billing.model.integration.claimmd.era.Charge;
import com.cob.billing.model.integration.claimmd.era.ClaimAdjustmentReasonCode;
import com.cob.billing.model.integration.claimmd.era.ERADetailsModel;
import com.cob.billing.usecases.bill.era.FindClaimAdjustmentReasonUseCase;
import com.cob.billing.usecases.bill.era.FindClaimStatusCodeUseCase;
import com.cob.billing.util.BeanFactory;

import java.util.ArrayList;
import java.util.List;

public class ERADetailsMapper {
    static List<String> linesReasonCodes = new ArrayList<>();

    public static ERADataDetailTransferModel map(ERADetailsModel eraDetailsModel) {

        List<ERALineTransferModel> lines = new ArrayList<>();
        eraDetailsModel.getClaim()
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
                        lines.add(lineTransferModel);
                    }

                });
        ERADataDetailTransferModel eraDataDetailTransferModel = ERADataDetailTransferModel.builder()
                .paymentMethod(eraDetailsModel.getPayment_method())
                .totalPaidAmount(Double.parseDouble(eraDetailsModel.getPaid_amount()))
                .lines(lines)
                .claimAdjustmentReasonCodes(getReasonCodes())
                .build();
        linesReasonCodes.clear();
        return eraDataDetailTransferModel;
    }

    private static double[] getAmounts(List<Adjustment> adjustments, double charge, double paid) {
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

    private static List<String> getReasons(List<Adjustment> adjustments) {
        List<String> reasons = new ArrayList<>();
        for (Adjustment adjustment : adjustments) {
            String reason = adjustment.getCode();
            reasons.add(reason);
        }
        linesReasonCodes.addAll(reasons);
        return reasons;
    }

    private static String getClaimDescription(String statusCode) {
        FindClaimStatusCodeUseCase findClaimStatusCodeUseCase = BeanFactory.getBean(FindClaimStatusCodeUseCase.class);
        return findClaimStatusCodeUseCase.find(statusCode);
    }

    private static List<ClaimAdjustmentReasonCode> getReasonCodes() {
        FindClaimAdjustmentReasonUseCase findClaimAdjustmentReasonUseCase = BeanFactory.getBean(FindClaimAdjustmentReasonUseCase.class);
        return findClaimAdjustmentReasonUseCase.findByCodes(linesReasonCodes);
    }
}
