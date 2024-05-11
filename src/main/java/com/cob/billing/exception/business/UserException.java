package com.cob.billing.exception.business;

import org.springframework.http.HttpStatus;

public class UserException extends BillingException{
    public static final String USER_IS_EXISTS = Category.Business.value() + getPrefix() +"_00";
    public static final String USER_EMAIL_IS_EXISTS = Category.Business.value() + getPrefix() +"_01";
    public UserException(String code) {
        super(code);
    }

    public UserException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public UserException(HttpStatus status, String code, Object[] parameters) {
        super(status, code, parameters);
    }

    protected static String getPrefix() {
        return "_user";
    }
}
