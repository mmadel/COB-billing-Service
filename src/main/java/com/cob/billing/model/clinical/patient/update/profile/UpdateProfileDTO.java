package com.cob.billing.model.clinical.patient.update.profile;

import com.cob.billing.model.clinical.patient.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateProfileDTO {
    private List<PatientInsuranceDTO> insurances;
    private List<PatientCaseDTO> cases;
    private List<PatientSessionDTO> sessions;
    private List<PatientAuthorizationDTO> authorizations;
    private Patient patient;
}
