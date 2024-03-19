package com.cob.billing.model.bill.auth.util;

import com.cob.billing.entity.clinical.patient.auth.PatientAuthorizationEntity;
import com.cob.billing.model.bill.auth.AuthorizationSession;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationMapper {
    public static AuthorizationSession map(PatientAuthorizationEntity patientAuthorization) {
        return AuthorizationSession.builder()
                .id(patientAuthorization.getId())
                .startDate(patientAuthorization.getStartDateNumber())
                .expiryDate(patientAuthorization.getExpireDateNumber())
                .remainingValue(patientAuthorization.getRemaining())
                .insuranceCompanyId(patientAuthorization.getPatientInsuranceCompany())
                .authorizationNumber(patientAuthorization.getAuthNumber())
                .build();
    }
    public static List<AuthorizationSession> map(List<PatientAuthorizationEntity> patientAuthorizations) {
        return patientAuthorizations.stream()
                .map(patientAuthorization -> map(patientAuthorization))
                .collect(Collectors.toList());

    }
}
