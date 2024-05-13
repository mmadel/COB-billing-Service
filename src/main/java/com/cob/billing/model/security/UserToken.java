package com.cob.billing.model.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserToken {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String refresh_expires_in;
    private String token_type;
}
