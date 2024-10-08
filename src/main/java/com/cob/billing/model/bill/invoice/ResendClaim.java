package com.cob.billing.model.bill.invoice;

import com.cob.billing.model.clinical.patient.Patient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ResendClaim {
    List<SelectedSessionServiceLine> serviceLines;
    Patient patient;
}
