package com.cob.billing.model.integration.claimmd.era;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class ERAListResponse {
    public List<ERAModel> era;
    public int last_eraid;
}
