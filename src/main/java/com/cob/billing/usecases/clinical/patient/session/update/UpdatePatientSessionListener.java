package com.cob.billing.usecases.clinical.patient.session.update;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaimServiceLine;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimServiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UpdatePatientSessionListener {
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;
    @Autowired
    PatientSubmittedClaimServiceLineRepository patientSubmittedClaimServiceLineRepository;

    @EventListener
    public void handleUserRegistrationEvent(SessionEvent sessionEvent) {
        List<ServiceLine> changedServiceLines = sessionEvent.getSession().getServiceCodes();
        List<PatientSubmittedClaim> submittedClaims = sessionEvent.getSubmittedSessions();
        List<PatientSubmittedClaimServiceLine> toBeUpdate = new ArrayList<>();
        submittedClaims.stream().forEach(patientSubmittedClaim -> {
            List<PatientSubmittedClaimServiceLine> submittedClaimServiceLines = patientSubmittedClaim.getServiceLine();
            // Loop through each updated ServiceLine
            changedServiceLines.forEach(updated -> {
                // Find matching saved ServiceLine by ID
                PatientSubmittedClaimServiceLine matchingSaved = submittedClaimServiceLines.stream()
                        .filter(saved -> Objects.equals(saved.getServiceLineId(), updated.getId()))
                        .findFirst()
                        .orElse(null);

                if (matchingSaved != null) {
                    // If matching saved ServiceLine exists, compare fields and update if needed
                    if (!Objects.equals(matchingSaved.getCptCode().getServiceCode(), updated.getCptCode().getServiceCode()) ||
                            !Objects.equals(matchingSaved.getCptCode().getModifier(), updated.getCptCode().getModifier()) ||
                            !Objects.equals(matchingSaved.getCptCode().getUnit(), updated.getCptCode().getUnit()) ||
                            !Objects.equals(matchingSaved.getCptCode().getCharge(), updated.getCptCode().getCharge()) ||
                            !Objects.equals(matchingSaved.getDiagnoses(), updated.getDiagnoses())) {

                        // Update the saved ServiceLine fields with the values from the updated ServiceLine
                        matchingSaved.getCptCode().setServiceCode(updated.getCptCode().getServiceCode());
                        matchingSaved.getCptCode().setModifier(updated.getCptCode().getModifier());
                        matchingSaved.getCptCode().setUnit(updated.getCptCode().getUnit());
                        matchingSaved.getCptCode().setCharge(updated.getCptCode().getCharge());
                        matchingSaved.setDiagnoses(updated.getDiagnoses());
                        toBeUpdate.add(matchingSaved);
                    }
                }
            });
        });
        patientSubmittedClaimServiceLineRepository.saveAll(toBeUpdate);
    }
}
