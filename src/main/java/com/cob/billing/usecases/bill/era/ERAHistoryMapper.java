package com.cob.billing.usecases.bill.era;

import com.cob.billing.model.bill.posting.era.ERAHistory;
import com.cob.billing.model.bill.posting.era.ERADataTransferModel;
import com.cob.billing.model.bill.posting.era.ERALineTransferModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ERAHistoryMapper {
    public ERAHistory map(ERADataTransferModel era) {
        ERAHistory eraHistory = new ERAHistory();
        eraHistory.setEraId(era.getEraId());
        List<Integer> lines = era.getEraDetails().getLines().stream()
                .map(ERALineTransferModel::getChargeLineId).collect(Collectors.toList());
        eraHistory.setEraLines(lines);
        eraHistory.setArchive(false);
        return eraHistory;
    }
}
