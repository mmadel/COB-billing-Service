package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.claim.PatientClaimEntity;
import com.cob.billing.model.bill.invoice.response.InvoiceResponse;
import com.cob.billing.repositories.bill.invoice.PatientClaimRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreatePatientClaimUseCase {
    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    private PatientClaimRepository patientClaimRepository;

    public void create(InvoiceResponse invoiceResponse) {
        List<PatientClaimEntity> patientClaims = invoiceResponse.getClaimSubmissions().stream()
                .map(claimSubmission -> {
                    PatientClaimEntity patientClaim = new PatientClaimEntity();
                    patientClaim.setClaimId(claimSubmission.getClaimId());
                    patientClaim.setSubmissionStatus(claimSubmission.getStatus());
                    patientClaim.setSubmissionMessages(claimSubmission.getMessages());
                    PatientInvoiceEntity patientInvoice =getPatientInvoiceRecord(claimSubmission.getServiceLines());
                    patientClaim.setPatientInvoice(patientInvoice);
                    return patientClaim;
                }).collect(Collectors.toList());
        patientClaimRepository.saveAll(patientClaims);
    }

    private PatientInvoiceEntity getPatientInvoiceRecord(List<Long> serviceLines){
        return patientInvoiceRepository.findByServiceLines(serviceLines)
                .orElseThrow(() -> new IllegalArgumentException("Patient Invoice is not found"));

    }
}
