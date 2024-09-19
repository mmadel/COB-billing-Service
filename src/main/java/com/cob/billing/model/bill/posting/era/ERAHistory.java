package com.cob.billing.model.bill.posting.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ERAHistory {
    private Long id;
    private List<ERALineTransferModel> historyLines;
    private boolean isArchive;
    private Long createdAt;
    private ERADataTransferModel era;
}
