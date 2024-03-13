package com.cob.billing.exception;

import com.cob.billing.exception.business.BillingException;
import com.cob.billing.exception.model.ErrorModel;
import com.cob.billing.exception.response.ControllerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    ResourceBundleMessageSource messageSource;

    @ExceptionHandler(value = {BillingException.class})
    @ResponseBody
    public ResponseEntity handleFeedbackExceptionException(BillingException ex, WebRequest request) {
        String errorMessage = messageSource.getMessage(ex.getCode(), ex.getParameters(), Locale.ENGLISH);
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(errorMessage, ex.getStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getStatus());
        log.error(controllerErrorResponse.getMessage());
        return new ResponseEntity(controllerErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(ex.getMessage(), status);
        log.error(controllerErrorResponse.getMessage());
        return new ResponseEntity<>(controllerErrorResponse, status);
    }

}
