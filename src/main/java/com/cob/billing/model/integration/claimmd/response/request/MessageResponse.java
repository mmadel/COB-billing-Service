package com.cob.billing.model.integration.claimmd.response.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private String status;
    private int responseid;
    private String message;
    private String fields;
    private String mesgid;
}
