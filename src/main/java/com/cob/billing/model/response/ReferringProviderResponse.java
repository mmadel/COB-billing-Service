package com.cob.billing.model.response;

import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.referring.provider.ReferringProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ReferringProviderResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<ReferringProvider> records;
}
