package com.cob.billing.model.bill.posting.era;

import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.ServiceLinePaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
public class ERALineTransferModel {
    private double billAmount;
    private double allowedAmount;
    // AdjustAmount = B1illAmount - (paidAmount - (deductAmount + coInsuranceAmount + coPaymentAmount))
    private double adjustAmount;
    private double editAdjustAmount;
    private double deductAmount;
    private double coInsuranceAmount;
    private double coPaymentAmount;
    private double paidAmount;
    private double editPaidAmount;
    private String[] reasons;
    private Long cptCode;
    private Integer units;
    private String dos;
    private String claimId;
    private String claimStatusCode;
    private String claimStatusDescription;
    private Integer serviceLineID;
    private String PatientName;
    private ServiceLinePaymentAction serviceLinePaymentAction;
    private String action;
    private Boolean selected;
    private boolean touched;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ERALineTransferModel that = (ERALineTransferModel) o;
        return serviceLineID.equals(that.serviceLineID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceLineID);
    }
}
