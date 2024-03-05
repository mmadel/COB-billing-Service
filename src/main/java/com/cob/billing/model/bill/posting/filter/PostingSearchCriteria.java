package com.cob.billing.model.bill.posting.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostingSearchCriteria {
    private Long entityId;
    private Long startDate;
    private Long endDate;
}