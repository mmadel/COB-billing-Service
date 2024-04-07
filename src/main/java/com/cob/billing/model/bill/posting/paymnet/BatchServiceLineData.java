package com.cob.billing.model.bill.posting.paymnet;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchServiceLineData {
    private Long dos;
    private String cpt;
    private String provider;
}

