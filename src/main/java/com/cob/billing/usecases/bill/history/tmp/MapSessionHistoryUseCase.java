package com.cob.billing.usecases.bill.history.tmp;

import com.cob.billing.entity.bill.invoice.submitted.PatientInvoiceRecord;
import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaimServiceLine;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.history.SessionHistoryCount;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MapSessionHistoryUseCase {
    @Autowired
    ModelMapper mapper;

    public List<SessionHistory> map(List<PatientInvoiceRecord> patientInvoiceRecords) {
        List<SessionHistory> sessionHistories = new ArrayList<>();
        for (PatientInvoiceRecord record : patientInvoiceRecords) {
            if (record.getClaims().size() > 1) {
                sessionHistories.addAll(hasClaimRejected(record.getClaims(), record));
            } else {
                SessionHistory sessionHistory = createHistoryRecord(record, null, null);
                sessionHistories.add(sessionHistory);
            }
        }
        return sessionHistories;
    }

    private SessionHistory createHistoryRecord(PatientInvoiceRecord patientInvoiceRecord, List<PatientSubmittedClaim> claims, String providerName) {
        SessionHistory sessionHistory = new SessionHistory();
        sessionHistory.setSubmissionId(patientInvoiceRecord.getSubmissionId());
        sessionHistory.setInsuranceCompany(patientInvoiceRecord.getInsuranceCompanyName());
        sessionHistory.setSubmitDate(patientInvoiceRecord.getCreatedAt());
        Patient patient = new Patient();
        patient.setId(patientInvoiceRecord.getPatientId());
        patient.setFirstName(patientInvoiceRecord.getPatientFirstName());
        patient.setLastName(patientInvoiceRecord.getPatientLastName());
        sessionHistory.setClient(patient);
        String provider = providerName != null ? providerName
                : patientInvoiceRecord.getClaims().stream().findFirst().get().getProviderFirstName() + "," + patientInvoiceRecord.getClaims().stream().findFirst().get().getProviderLastName();
        sessionHistory.setProvider(provider);
        sessionHistory.setSubmissionType(patientInvoiceRecord.getSubmissionType());
        if (claims == null) {
            sessionHistory.setSubmissionStatus(patientInvoiceRecord.getClaims().stream().findFirst().get().getSubmissionStatus());
            sessionHistory.setSessionCounts(createSessionHistoryCount(patientInvoiceRecord.getClaims()));
        } else {
            sessionHistory.setSubmissionStatus(claims.stream().findFirst().get().getSubmissionStatus());
            sessionHistory.setSessionCounts(createSessionHistoryCount(new HashSet<>(claims)));
        }
        return sessionHistory;
    }

    private List<SessionHistoryCount> createSessionHistoryCount(Set<PatientSubmittedClaim> claims) {
        List<SessionHistoryCount> counts = new ArrayList<>();
        for (PatientSubmittedClaim claim : claims) {
            SessionHistoryCount count = new SessionHistoryCount();
            count.setSessionId(claim.getLocalClaimId());
            count.setServiceLines(claim.getServiceLine().stream().count());
            count.setServiceLine(getServiceLines(claim.getServiceLine()));
            count.setDateOfService(claim.getDateOfService());
            counts.add(count);
        }
        return counts;
    }

    private List<SessionHistory> hasClaimRejected(Set<PatientSubmittedClaim> claims, PatientInvoiceRecord record) {
        List<SessionHistory> sessionHistories = new ArrayList<>();
        // Split the list into two lists based on the status
        List<PatientSubmittedClaim> rejectedClaims = claims.stream()
                .filter(claim -> claim.getSubmissionStatus() != null && claim.getSubmissionStatus().equals(SubmissionStatus.error))
                .collect(Collectors.toList());
        if (!rejectedClaims.isEmpty())
            sessionHistories.addAll(hasClaimsWithMultipleProviders(new HashSet<>(rejectedClaims), record));


        List<PatientSubmittedClaim> otherClaims = claims.stream()
                .filter(claim -> claim.getSubmissionStatus() != null && (!claim.getSubmissionStatus().equals(SubmissionStatus.error)))
                .collect(Collectors.toList());

        if (!otherClaims.isEmpty())
            sessionHistories.addAll(hasClaimsWithMultipleProviders(new HashSet<>(otherClaims), record));
        return sessionHistories;
    }

    private List<SessionHistory> hasClaimsWithMultipleProviders(Set<PatientSubmittedClaim> claims, PatientInvoiceRecord record) {
        List<SessionHistory> sessionHistories = new ArrayList<>();
        if (claims.size() == 1) {
            PatientSubmittedClaim claim = claims.stream().findFirst().get();
            String providerName = claim.getProviderLastName() + "," + claim.getProviderFirstName();
            sessionHistories.add(createHistoryRecord(record, new ArrayList<>(claims), providerName));
            return sessionHistories;
        }
        // Assume the first provider_npi as the reference
        String referenceProviderNpi = claims.stream().findFirst().get().getProvider_npi();

        // Partition the list based on whether provider_npi matches the reference
        Map<Boolean, List<PatientSubmittedClaim>> partitionedClaims = claims.stream()
                .collect(Collectors.partitioningBy(claim -> referenceProviderNpi.equals(claim.getProvider_npi())));

        // List where provider_npi matches the reference
        List<PatientSubmittedClaim> matchingProviderNpiList = partitionedClaims.get(true);

        // List where provider_npi differs from the reference
        List<PatientSubmittedClaim> differentProviderNpiList = partitionedClaims.get(false);

        if (!matchingProviderNpiList.isEmpty())
            sessionHistories.add(createHistoryRecord(record, matchingProviderNpiList, matchingProviderNpiList.stream().findFirst().get().getProviderLastName()
                    + ","
                    + matchingProviderNpiList.stream().findFirst().get().getProviderFirstName()));
        if (!differentProviderNpiList.isEmpty())
            sessionHistories.add(createHistoryRecord(record, differentProviderNpiList, differentProviderNpiList.stream().findFirst().get().getProviderLastName()
                    + ","
                    + differentProviderNpiList.stream().findFirst().get().getProviderFirstName()));
        return sessionHistories;
    }

    private List<ServiceLine> getServiceLines(List<PatientSubmittedClaimServiceLine> submittedClaimServiceLines) {
        return submittedClaimServiceLines.stream()
                .map(patientSubmittedClaimServiceLine -> {
                    ServiceLine serviceLine = mapper.map(patientSubmittedClaimServiceLine, ServiceLine.class);
                    serviceLine.setId(patientSubmittedClaimServiceLine.getServiceLineId());
                    return serviceLine;
                }).collect(Collectors.toList());
    }
}
