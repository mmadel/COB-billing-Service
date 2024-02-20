package com.cob.billing.model.bill.payer;

import com.cob.billing.enums.PayerType;
import com.cob.billing.model.common.BasicAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payer {
    private Long id;
    private String name;
    private String displayName;
    private Long payerId;
    private BasicAddress address;
    private PayerType payerType;
}
