package com.cob.billing.exception.business;

import org.springframework.http.HttpStatus;

public class UserException extends BillingException{
    public static final String USER_IS_EXISTS = Category.Business.value() + getPrefix() +"_00";
    public static final String USER_EMAIL_IS_EXISTS = Category.Business.value() + getPrefix() +"_01";
    public static final String USER_NOT_CREATED_IN_KC = Category.Business.value() + getPrefix() +"_02";
    public static final String INVALID_PASSWORD = Category.Business.value() + getPrefix() +"_03";
    public static final String USER_NOT_FOUND = Category.Business.value() + getPrefix() +"_04";

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
