package com.cob.billing.exception.model;

import com.cob.billing.exception.business.BillingException;

import com.cob.billing.util.BeanFactory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Locale;

@JsonIgnoreProperties(value = {"exception"})
public class ErrorModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private BillingException exception;
    private MessageSource messageSource;

    private ErrorModel() {
        timestamp = LocalDateTime.now();
        messageSource = BeanFactory.getBean(ResourceBundleMessageSource.class);
    }

    public ErrorModel(BillingException exception) {
        this();
        this.status = exception.getStatus();
        this.exception = exception;
        this.message =
                exception.getParameters() == null ?
                        getMessage(exception.getCode()) :
                        getMessage(exception.getCode(), exception.getParameters());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    private String getMessage(String code, Object[] parameters) {
        return messageSource.getMessage(code, parameters, Locale.ENGLISH);
    }
}
