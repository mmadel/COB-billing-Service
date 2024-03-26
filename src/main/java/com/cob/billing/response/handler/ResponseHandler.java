package com.cob.billing.response.handler;

import com.cob.billing.model.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        if (responseObj != null)
            map.put("records", responseObj);
        map.put("time-stamp", new Date().getTime());

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, PatientResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, PatientSessionServiceLineResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, MinimalPatientResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, InvoicePatientSessionResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, SessionHistoryResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, ClientPostingPaymentResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, ClinicResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, FeeScheduleLineResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, ReferringProviderResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, ProviderResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, PatientSessionResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    protected static Map<String, Object> populateResponseMap(String message, HttpStatus status,
                                                             Integer numberOfRecords, Integer numberOfMatchingRecords) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("number_of_records", numberOfRecords);
        map.put("number_of_matching_records", numberOfMatchingRecords);
        map.put("status", status.value());
        map.put("time-stamp", new Date().getTime());
        return map;
    }
}
