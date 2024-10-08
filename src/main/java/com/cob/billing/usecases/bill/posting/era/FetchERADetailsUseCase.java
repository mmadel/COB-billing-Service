package com.cob.billing.usecases.bill.posting.era;

import com.cob.billing.entity.bill.era.ERAHistoryEntity;
import com.cob.billing.model.bill.posting.era.ERADataDetailTransferModel;
import com.cob.billing.model.bill.posting.era.ERALineTransferModel;
import com.cob.billing.model.integration.claimmd.era.Claim;
import com.cob.billing.model.integration.claimmd.era.ClaimAdjustmentReasonCode;
import com.cob.billing.model.integration.claimmd.era.ERADetailsModel;
import com.cob.billing.usecases.bill.era.FindClaimAdjustmentReasonUseCase;
import com.cob.billing.usecases.integration.claim.md.RetrieveERADetailsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FetchERADetailsUseCase {
    @Autowired
    private RetrieveERADetailsUseCase retrieveERADetailsUseCase;
    @Autowired
    private ConstructEARDetailsLinesUseCase constructEARDetailsLinesUseCase;
    @Autowired
    private EnrichERADetailsLinesWithEditPaymentsUseCase enrichERADetailsLinesWithEditPaymentsUseCase;
    @Autowired
    private FindClaimAdjustmentReasonUseCase findClaimAdjustmentReasonUseCase;
    private Integer totalLines;

    public ERADataDetailTransferModel fetch(Integer eraid, List<ERAHistoryEntity> eraList) {
        ERADetailsModel eraDetailsModel = retrieveERADetailsUseCase.get(eraid);
        Map<String, List<ERALineTransferModel>> patientLines = getLinesPerPatient(eraDetailsModel.getClaim(), eraid, eraList);
        Map<String, List<ClaimAdjustmentReasonCode>> patientClaimAdjustmentReasonCodes = getReasonCodesPerPatient(patientLines);
        return ERADataDetailTransferModel.builder()
                .paymentMethod(eraDetailsModel.getPayment_method())
                .totalPaidAmount(Double.parseDouble(eraDetailsModel.getPaid_amount()))
                .totalLines(totalLines)
                .patientLines(patientLines)
                .patientReasonCodes(patientClaimAdjustmentReasonCodes)
                .build();

    }

    private Map<String, List<ERALineTransferModel>> getLinesPerPatient(List<Claim> claims, Integer eraid, List<ERAHistoryEntity> eraList) {
        List<ERALineTransferModel> lines = constructEARDetailsLinesUseCase.constructLines(claims);
        totalLines = lines.size();
        enrichERADetailsLinesWithEditPaymentsUseCase.enrichLines(lines, eraList, eraid);
        return lines.stream()
                .collect(Collectors.groupingBy(ERALineTransferModel::getPatientName));
    }

    private Map<String, List<ClaimAdjustmentReasonCode>> getReasonCodesPerPatient(Map<String, List<ERALineTransferModel>> linesPerPatient) {
        Map<String, List<ClaimAdjustmentReasonCode>> patientReasonCodes = new HashMap<>();
        for (Map.Entry<String, List<ERALineTransferModel>> entry : linesPerPatient.entrySet()) {
            List<String> reasonCodes = entry.getValue().stream()
                    .flatMap(model -> Stream.of(model.getReasons()))
                    .distinct()
                    .collect(Collectors.toList());
            List<ClaimAdjustmentReasonCode> claimAdjustmentReasonCodes = findClaimAdjustmentReasonUseCase.findByCodes(reasonCodes);
            patientReasonCodes.put(entry.getKey(), claimAdjustmentReasonCodes);
        }
        return patientReasonCodes;
    }
}
