package com.cob.billing.model.bill.invoice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class InvoiceGenerationResponse {
    List<String> files;
    List<Long> records;
}
