package com.cob.billing.model.integration.claimmd;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Claim {
    public String accept_assign;
    public String auto_accident;
    public String balance_due;
    public String bill_addr_1;
    public String bill_city;
    public String bill_name;
    public String bill_npi;
    public String bill_phone;
    public String bill_state;
    public String bill_taxid;
    public String bill_taxid_type;
    public String bill_zip;
    public List<Charge> charge;
    public String claim_form;
    public String diag_1;
    public String diag_2;
    public String diag_3;
    public String diag_4;
    public String employment_related;
    public String ins_addr_1;
    public String ins_city;
    public String ins_dob;
    public String ins_group;
    public String ins_name_f;
    public String ins_name_l;
    public String ins_number;
    public String ins_sex;
    public String ins_state;
    public String ins_zip;
    public String pat_addr_1;
    public String pat_city;
    public String pat_dob;
    public String pat_name_f;
    public String pat_name_l;
    public String pat_rel;
    public String pat_sex;
    public String pat_state;
    public String pat_zip;
    public String payerid;
    public String pcn;
    public String prov_name_f;
    public String prov_name_l;
    public String prov_name_m;
    public String prov_npi;
    public String prov_taxonomy;
    public String ref_name_f;
    public String ref_name_l;
    public String ref_name_m;
    public String ref_npi;
    public String remote_batchid;
    public String remote_claimid;
    public String remote_fileid;
    public String total_charge;
    public String clia_number;
    public String payer_addr_1;
    public String payer_city;
    public String payer_name;
    public String payer_order;
    public String payer_state;
    public String payer_zip;
}
