package com.cob.billing.model.integration.claimmd.era;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ERAModel {
    private String prov_npi;
    private String payer_name;
    private String check_number;
    private String paid_date;
    private int eraid;
    private String paid_amount;
    private String prov_taxid;
    private String download_time;
    private String prov_name;
    private String claimmd_prov_name;
    private String check_type;
    private String payerid;
    private String received_time;
}
