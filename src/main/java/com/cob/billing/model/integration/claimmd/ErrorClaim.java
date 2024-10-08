package com.cob.billing.model.integration.claimmd;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorClaim {
    private String error_code;
    private String error_message;
}
