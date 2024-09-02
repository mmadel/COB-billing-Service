package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.tmp.PatientInvoiceRecord;
import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.invoice.response.tmp.InvoiceResponse;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.integration.claimmd.ClaimResponse;
import com.cob.billing.repositories.bill.invoice.tmp.PatientInvoiceRecordRepository;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class CreatPatientInvoiceRecordUseCase {
    @Autowired
    private PatientInvoiceRecordRepository patientInvoiceRecordRepository;

    @Autowired
    private PatientSubmittedClaimRepository patientSubmittedClaimRepository;
    @Autowired
    CreateCMSFileUseCase createCMSFileUseCase;

    public void create(InvoiceRequest invoiceRequest, InvoiceResponse invoiceResponse) throws IOException {
        PatientInvoiceRecord patientInvoiceRecord = createPatientInvoiceRecord(invoiceRequest, invoiceResponse.getClaimsFileNames());
        creatPatientSubmittedClaims(invoiceRequest.getSubmissionType(), invoiceResponse, patientInvoiceRecord);
    }

    private PatientInvoiceRecord createPatientInvoiceRecord(InvoiceRequest invoiceRequest, List<String> claimsFileNames) throws IOException {

        PatientInvoiceRecord patientInvoiceRecord = new PatientInvoiceRecord();
        String patientName = invoiceRequest.getPatientInformation().getLastName() + "," + invoiceRequest.getPatientInformation().getFirstName();
        patientInvoiceRecord.setPatientFirstName(invoiceRequest.getPatientInformation().getFirstName());
        patientInvoiceRecord.setPatientLastName(invoiceRequest.getPatientInformation().getLastName());
        patientInvoiceRecord.setPatientId(invoiceRequest.getPatientInformation().getId());
        patientInvoiceRecord.setInsuranceCompanyName(invoiceRequest.getInvoiceInsuranceCompanyInformation().getName());
        //patientInvoiceRecord.setInsuranceCompanyId();
        patientInvoiceRecord.setSubmissionType(invoiceRequest.getSubmissionType());
        patientInvoiceRecord.setSubmissionId(generateSubmissionId());
        byte[] cmsDocument = createCMSFileUseCase.upload(claimsFileNames);
        patientInvoiceRecord.setCmsDocument(cmsDocument);
        return patientInvoiceRecordRepository.save(patientInvoiceRecord);
    }

    private void creatPatientSubmittedClaims(SubmissionType submissionType, InvoiceResponse invoiceResponse,
                                             PatientInvoiceRecord patientInvoiceRecord) {
        List<PatientSubmittedClaim> claims = new ArrayList<>();
        for (Map.Entry<PatientSession, List<SelectedSessionServiceLine>> entry : invoiceResponse.getSessions().entrySet()) {
            PatientSession session = entry.getKey();
            List<SelectedSessionServiceLine> serviceLines = entry.getValue();
            PatientSubmittedClaim claim = new PatientSubmittedClaim();
            claim.setProviderLastName(session.getDoctorInfo().getDoctorLastName());
            claim.setProviderFirstName(session.getDoctorInfo().getDoctorFirstName());
            claim.setProvider_npi(session.getDoctorInfo().getDoctorNPI());
            claim.setServiceLine(serviceLines.stream()
                    .map(SelectedSessionServiceLine::getServiceLine)
                    .collect(Collectors.toList()));
            claim.setPatientInvoiceRecord(patientInvoiceRecord);
            claim.setLocalClaimId(session.getId());
            claim.setDateOfService(serviceLines.stream().findFirst().get().getSessionId().getServiceDate());
            switch (submissionType) {
                case Print:
                    claim.setSubmissionStatus(SubmissionStatus.Success);
                    break;
                case Electronic:
                    ClaimResponse claimResponse = getClaim(session.getId(), invoiceResponse.getClearingHouseClaimsResponse().getClaim());
                    claim.setSubmissionStatus(getClaimStatus(claimResponse));
                    claim.setMessages(getClaimMessage(claimResponse));
                    claim.setRemoteClaimId(Long.parseLong(claimResponse.getClaimmd_id()));
                    break;
            }
            claims.add(claim);
        }
        patientSubmittedClaimRepository.saveAll(claims);
    }

    private Long generateSubmissionId() {
        Random rand = new Random();
        return Long.parseLong(String.format("%04d", rand.nextInt(10000)));
    }

    private ClaimResponse getClaim(Long sessionId, List<ClaimResponse> claimResponses) {
        return claimResponses.stream()
                .filter(claimResponse -> claimResponse.getRemote_claimid().equals(sessionId.toString()))
                .findFirst().get();
    }

    private SubmissionStatus getClaimStatus(ClaimResponse claimResponse) {
        switch (claimResponse.getStatus()) {
            case "A":
                return SubmissionStatus.Pending;
            case "R":
                return SubmissionStatus.error;
            default:
                throw new IllegalArgumentException("Un-recognized Status");
        }
    }

    private List<String> getClaimMessage(ClaimResponse claimResponse) {
        return claimResponse.getMessages().stream()
                .map(messageResponse -> messageResponse.getMessage())
                .collect(Collectors.toList());
    }
}
