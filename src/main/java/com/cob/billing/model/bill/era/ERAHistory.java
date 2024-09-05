package com.cob.billing.model.bill.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ERAHistory {
    private Long id;
    private Integer eraId;
    private List<Long> eraLines;
    private boolean isArchive;
    private Long createdAt;
}
