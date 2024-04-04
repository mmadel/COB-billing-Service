package com.cob.billing.model.response;

import com.cob.billing.model.bill.posting.BatchServiceLinePayment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ClientPostingPaymentResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<BatchServiceLinePayment> records;
}
