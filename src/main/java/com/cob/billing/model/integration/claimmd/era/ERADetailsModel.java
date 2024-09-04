package com.cob.billing.model.integration.claimmd.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ERADetailsModel {
    private String prov_routing;
    private int eraid;
    private String payer_state;
    private String prov_npi;
    private String check_number;
    private String prov_addr_1;
    private String payer_zip;
    private String eft_sender_id;
    private String prov_state;
    private String prov_taxid;
    private String payer_addr_1;
    private String payer_account;
    private String payer_name;
    private String prov_name;
    private String payerid;
    private String prov_account;
    private String payer_city;
    private String prov_city;
    private String payer_routing;
    private String prov_zip;
    private String payment_method;
    private List<Claim> claim;
    private String payer_companyid;
    private String paid_date;
    private String payment_format;
    private String paid_amount;
}
