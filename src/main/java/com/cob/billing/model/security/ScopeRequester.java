package com.cob.billing.model.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ScopeRequester {
    private String uuid;
    private List<String> roles;
}
