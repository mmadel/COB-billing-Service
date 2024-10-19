package com.cob.billing.exception.business;

import org.springframework.http.HttpStatus;

public class OrganizationException extends BillingException {
    public OrganizationException(String code) {
        super(code);
    }

    public OrganizationException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public OrganizationException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_org";
    }
}
