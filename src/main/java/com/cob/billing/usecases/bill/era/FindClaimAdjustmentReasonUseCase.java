package com.cob.billing.usecases.bill.era;

import com.cob.billing.entity.bill.era.ClaimAdjustmentReasonCodeEntity;
import com.cob.billing.model.integration.claimmd.era.ClaimAdjustmentReasonCode;
import com.cob.billing.repositories.bill.era.ClaimAdjustmentReasonCodeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClaimAdjustmentReasonUseCase {
    @Autowired
    private ClaimAdjustmentReasonCodeRepository claimAdjustmentReasonCodeRepository;
    @Autowired
    ModelMapper mapper;

    public ClaimAdjustmentReasonCode find(String code){

        return mapper.map(claimAdjustmentReasonCodeRepository.findByCode(code).get(),ClaimAdjustmentReasonCode.class);
    }

    public List<ClaimAdjustmentReasonCode> findByCodes(List<String> codes){
        return claimAdjustmentReasonCodeRepository.findByCodes(codes).get().stream()
                .map(claimAdjustmentReasonCodeEntity -> mapper.map(claimAdjustmentReasonCodeEntity,ClaimAdjustmentReasonCode.class))
                .collect(Collectors.toList());
    }
}
