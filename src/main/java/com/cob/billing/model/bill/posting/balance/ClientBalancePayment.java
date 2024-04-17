package com.cob.billing.model.bill.posting.balance;

import com.cob.billing.model.clinical.patient.session.PatientSession;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ClientBalancePayment extends ClientBalanceModel{
    private Long dos;
    private String serviceCode;
    private String provider;
    private Double charge;
    private Double insCompanyPayment;
    private Double clientPayment;
    private Double adjustPayment;
    private Double balance;
    private String placeOfCode;
    private Integer units;
    private ClientBalanceAccount clientBalanceAccount;
    private Long sessionId;
    private PatientSession patientSession;
    private String loc;
}
