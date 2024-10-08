package com.cob.billing.model.integration.claimmd.era.respose;

import com.cob.billing.model.integration.claimmd.era.ERAModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class ERAListResponse {
    public List<ERAModel> era;
    public int last_eraid;
}
