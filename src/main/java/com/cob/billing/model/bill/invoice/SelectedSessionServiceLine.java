package com.cob.billing.model.bill.invoice;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SelectedSessionServiceLine {
    private Long sessionId;
    private Long serviceLine;
}
