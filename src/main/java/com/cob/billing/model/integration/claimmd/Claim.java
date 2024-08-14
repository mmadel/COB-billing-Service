package com.cob.billing.model.integration.claimmd;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Claim implements Serializable {
    private String accept_assign;
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
    private String ins_addr_1;
    private String ins_addr_2;
    private String ins_city;
    private String ins_country;
    private String ins_dob;
    private String ins_group;
    private String ins_plan;
    private String ins_name_f;
    private String ins_name_l;
    private String ins_name_m;
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
    private String ref_id;
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
    private String other_claimfilingcode;
    private String other_pat_rel;
    private String other_ins_name_l;
    private String other_ins_name_f;
    private String other_ins_name_m;
    private String other_ins_number;
    private String other_payer_name;
    private String other_payerid;
    private String ins_employer;
    private String cond_date;
    private String lastseen_date;
    private String onset_date;
    private String initial_treatment_date;
    private String nowork_from_date;
    private String nowork_to_date;
    private String hosp_from_date;
    private String hosp_thru_date;
    private String employment_related;
    private String auto_accident;
    private String auto_accident_state;
    private String other_accident;
    private String narrative;


    @Override
    public String toString() {
        return "Claim{" +
                "accept_assign='" + accept_assign + '\'' +
                ", auto_accident='" + auto_accident + '\'' +
                ", onset_date='" + onset_date + '\'' +
                ", accident_date='" + accident_date + '\'' +
                ", info_release='" + info_release + '\'' +
                ", balance_due='" + balance_due + '\'' +
                ", bill_addr_1='" + bill_addr_1 + '\'' +
                ", bill_city='" + bill_city + '\'' +
                ", bill_name='" + bill_name + '\'' +
                ", bill_npi='" + bill_npi + '\'' +
                ", bill_phone='" + bill_phone + '\'' +
                ", bill_state='" + bill_state + '\'' +
                ", bill_taxid='" + bill_taxid + '\'' +
                ", bill_taxid_type='" + bill_taxid_type + '\'' +
                ", bill_zip='" + bill_zip + '\'' +
                ", charge=" + charge +
                ", claim_form='" + claim_form + '\'' +
                ", employment_related='" + employment_related + '\'' +
                ", ins_addr_1='" + ins_addr_1 + '\'' +
                ", ins_city='" + ins_city + '\'' +
                ", ins_dob='" + ins_dob + '\'' +
                ", ins_group='" + ins_group + '\'' +
                ", ins_name_f='" + ins_name_f + '\'' +
                ", ins_name_l='" + ins_name_l + '\'' +
                ", ins_number='" + ins_number + '\'' +
                ", ins_sex='" + ins_sex + '\'' +
                ", ins_state='" + ins_state + '\'' +
                ", ins_zip='" + ins_zip + '\'' +
                ", pat_addr_1='" + pat_addr_1 + '\'' +
                ", pat_city='" + pat_city + '\'' +
                ", pat_dob='" + pat_dob + '\'' +
                ", pat_name_f='" + pat_name_f + '\'' +
                ", pat_name_l='" + pat_name_l + '\'' +
                ", pat_rel='" + pat_rel + '\'' +
                ", pat_sex='" + pat_sex + '\'' +
                ", pat_state='" + pat_state + '\'' +
                ", pat_zip='" + pat_zip + '\'' +
                ", payerid='" + payerid + '\'' +
                ", payer_addr_1='" + payer_addr_1 + '\'' +
                ", payer_city='" + payer_city + '\'' +
                ", payer_name='" + payer_name + '\'' +
                ", payer_order='" + payer_order + '\'' +
                ", payer_state='" + payer_state + '\'' +
                ", payer_zip='" + payer_zip + '\'' +
                ", pcn='" + pcn + '\'' +
                ", prov_name_f='" + prov_name_f + '\'' +
                ", prov_name_l='" + prov_name_l + '\'' +
                ", prov_name_m='" + prov_name_m + '\'' +
                ", prov_npi='" + prov_npi + '\'' +
                ", prov_taxonomy='" + prov_taxonomy + '\'' +
                ", ref_name_f='" + ref_name_f + '\'' +
                ", ref_name_l='" + ref_name_l + '\'' +
                ", ref_name_m='" + ref_name_m + '\'' +
                ", ref_npi='" + ref_npi + '\'' +
                ", remote_batchid='" + remote_batchid + '\'' +
                ", remote_claimid='" + remote_claimid + '\'' +
                ", remote_fileid='" + remote_fileid + '\'' +
                ", total_charge='" + total_charge + '\'' +
                ", clia_number='" + clia_number + '\'' +
                ", facility_name='" + facility_name + '\'' +
                ", facility_addr_1='" + facility_addr_1 + '\'' +
                ", facility_addr_2='" + facility_addr_2 + '\'' +
                ", facility_city='" + facility_city + '\'' +
                ", facility_state='" + facility_state + '\'' +
                ", facility_zip='" + facility_zip + '\'' +
                ", facility_npi='" + facility_npi + '\'' +
                ", facility_id='" + facility_id + '\'' +
                ", diag_1='" + diag_1 + '\'' +
                ", diag_2='" + diag_2 + '\'' +
                ", diag_3='" + diag_3 + '\'' +
                ", diag_4='" + diag_4 + '\'' +
                ", diag_5='" + diag_5 + '\'' +
                ", diag_6='" + diag_6 + '\'' +
                ", diag_7='" + diag_7 + '\'' +
                ", diag_8='" + diag_8 + '\'' +
                ", diag_9='" + diag_9 + '\'' +
                ", diag_10='" + diag_10 + '\'' +
                ", diag_11='" + diag_11 + '\'' +
                ", diag_12='" + diag_12 + '\'' +
                '}';
    }
}
