package com.cob.billing.usecases.bill.posting.era;

import com.cob.billing.entity.bill.era.ERAHistoryEntity;
import com.cob.billing.model.bill.posting.era.ERALineTransferModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class EnrichERADetailsLinesWithEditPaymentsUseCase {
    public void enrichLines(List<ERALineTransferModel> lines, List<ERAHistoryEntity> historyList, Integer eraId) {
        List<ERALineTransferModel> serviceLines = historyList.stream()
                .filter(eraHistoryEntity -> eraHistoryEntity.getEraId().equals(eraId)).findFirst()
                .map(eraHistoryEntity -> eraHistoryEntity.getHistoryLines().stream()
                        .collect(Collectors.toList())).orElse(null);
        if (serviceLines != null) {
            for (ERALineTransferModel line : lines) {
                ERALineTransferModel matchedLineHistory = getMatchedLineHistory(serviceLines, line.getServiceLineID());
                if (matchedLineHistory != null) {
                    line.setTouched(true);
                    line.setEditPaidAmount(matchedLineHistory.getEditPaidAmount());
                    line.setEditAdjustAmount(matchedLineHistory.getEditAdjustAmount());
                    line.setAction(matchedLineHistory.getAction());
                }
            }
        }
    }

    private ERALineTransferModel getMatchedLineHistory(List<ERALineTransferModel> historyLines, Integer serviceLine) {
        for (ERALineTransferModel historyLine : historyLines) {
            if (historyLine.getServiceLineID().equals(serviceLine))
                return historyLine;
        }
        return null;
    }
}
