package com.cob.billing.model.integration.claimmd.response.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClaimResponse {
    private String claimmd_id;
    private String status;
    private String fdos;
    private String senderid;
    private String bill_npi;
    private String batchid;
    private String fileid;
    private String filename;
    private List<MessageResponse> messages;
    private String payerid;
    private String claimid;
    private String bill_taxid;
    private String remote_claimid;
    private String response_time;
    private String pcn;
    private String total_charge;
    private String ins_number;
    private String sender_icn;
    private String sender_name;
}
