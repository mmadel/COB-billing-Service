package com.cob.billing.model.integration.claimmd.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Claim {
    private Object place_of_service;
    private String from_dos;
    private Object plan_type;
    private String pat_name_f;
    private String ins_name_f;
    private String ins_number;
    private String total_charge;
    private String prov_npi;
    private List<Charge> charge;
    private Object pat_name_m;
    private String ins_name_m;
    private String filing_code;
    private String payer_icn;
    private Object crossover_id;
    private String patient_responsibility;
    private String claim_received_date;
    private String status_code;
    private Object thru_dos;
    private String ins_name_l;
    private String pat_name_l;
    private Object crossover_carrier;
    private String total_paid;
    private Object frequency_code;
    private String pcn;
}
