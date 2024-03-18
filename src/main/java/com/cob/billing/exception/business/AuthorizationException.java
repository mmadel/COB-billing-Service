package com.cob.billing.exception.business;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends BillingException {
    public static final String SESSION_AUTH_OUT_OF_RANGE = Category.Business.value() + getPrefix() + "_00";
    public static final String SESSION_AUTH_NO_REMAINING = Category.Business.value() + getPrefix() + "_01";
    public static final String SESSION_AUTH_INVALID_INSURANCE_COMPANY = Category.Business.value() + getPrefix() + "_02";
    public static final String SESSION_NO_MATCHED_AUTH = Category.Business.value() + getPrefix() + "_03";
    public static final String SESSION_AUTH_OVERLAP = Category.Business.value() + getPrefix() + "_04";

    public AuthorizationException(String code) {
        super(code);
    }

    public AuthorizationException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public AuthorizationException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_auth";
    }

}

