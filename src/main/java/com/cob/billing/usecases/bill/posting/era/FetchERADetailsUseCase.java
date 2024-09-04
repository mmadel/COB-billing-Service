package com.cob.billing.usecases.bill.posting.era;

import com.cob.billing.model.bill.posting.era.ERADataDetailTransferModel;
import com.cob.billing.model.integration.claimmd.era.ERADetailsModel;
import com.cob.billing.usecases.bill.posting.era.mapper.ERADetailsMapper;
import com.cob.billing.usecases.integration.claim.md.RetrieveERADetailsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchERADetailsUseCase {
    @Autowired
    RetrieveERADetailsUseCase retrieveERADetailsUseCase;

    public ERADataDetailTransferModel fetch(Integer eraid) {
        ERADetailsModel eraDetailsModel = retrieveERADetailsUseCase.get(eraid);
        return ERADetailsMapper.map(eraDetailsModel);
    }
}
