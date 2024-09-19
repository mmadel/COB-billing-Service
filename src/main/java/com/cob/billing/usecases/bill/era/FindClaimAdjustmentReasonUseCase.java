package com.cob.billing.usecases.bill.era;

import com.cob.billing.entity.bill.era.ClaimAdjustmentReasonCodeEntity;
import com.cob.billing.repositories.bill.era.ClaimAdjustmentReasonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindClaimAdjustmentReasonUseCase {
    @Autowired
    private ClaimAdjustmentReasonCodeRepository claimAdjustmentReasonCodeRepository;

    public ClaimAdjustmentReasonCodeEntity find(String code){
        return claimAdjustmentReasonCodeRepository.findByCode(code).get();
    }

    public List<ClaimAdjustmentReasonCodeEntity> findByCodes(List<String> codes){
        return claimAdjustmentReasonCodeRepository.findByCodes(codes).get();
    }
}
