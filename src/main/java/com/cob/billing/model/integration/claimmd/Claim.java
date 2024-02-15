package com.cob.billing.model.integration.claimmd;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Claim {
    private String accept_assign;
    private String auto_accident;
    private String onset_date;
    private String accident_date;
    private String info_release;
    private String balance_due;
    private String bill_addr_1;
    private String bill_city;
    private String bill_name;
    private String bill_npi;
    private String bill_phone;
    private String bill_state;
    private String bill_taxid;
    private String bill_taxid_type;
    private String bill_zip;
    private List<Charge> charge;
    private String claim_form;
    private String employment_related;
    private String ins_addr_1;
    private String ins_city;
    private String ins_dob;
    private String ins_group;
    private String ins_name_f;
    private String ins_name_l;
    private String ins_number;
    private String ins_sex;
    private String ins_state;
    private String ins_zip;
    private String pat_addr_1;
    private String pat_city;
    private String pat_dob;
    private String pat_name_f;
    private String pat_name_l;
    private String pat_rel;
    private String pat_sex;
    private String pat_state;
    private String pat_zip;
    private String payerid;
    private String payer_addr_1;
    private String payer_city;
    private String payer_name;
    private String payer_order;
    private String payer_state;
    private String payer_zip;
    private String pcn;
    private String prov_name_f;
    private String prov_name_l;
    private String prov_name_m;
    private String prov_npi;
    private String prov_taxonomy;
    private String ref_name_f;
    private String ref_name_l;
    private String ref_name_m;
    private String ref_npi;
    private String remote_batchid;
    private String remote_claimid;
    private String remote_fileid;
    private String total_charge;
    private String clia_number;
    private String facility_name;
    private String facility_addr_1;
    private String facility_addr_2;
    private String facility_city;
    private String facility_state;
    private String facility_zip;
    private String facility_npi;
    private String facility_id;
    private String diag_1;
    private String diag_2;
    private String diag_3;
    private String diag_4;
    private String diag_5;
    private String diag_6;
    private String diag_7;
    private String diag_8;
    private String diag_9;
    private String diag_10;
    private String diag_11;
    private String diag_12;


}
