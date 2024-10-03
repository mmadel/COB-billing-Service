package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.tmp.PatientSubmittedClaim;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.invoice.ResendClaim;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.repositories.bill.invoice.tmp.PatientSubmittedClaimRepository;
import com.cob.billing.usecases.clinical.patient.FindPatientUseCase;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrepareClaimResendUseCase {
    @Autowired
    FindPatientUseCase findPatientUseCase;
    @Autowired
    PatientSubmittedClaimRepository patientSubmittedClaimRepository;
    @Autowired
    ModelMapper mapper;

    public ResendClaim prepare(Long patientId, Long submissionId) {
        this.mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<PatientSessionEntity, PatientSession>() {
            @Override
            protected void configure() {
                skip(
                        destination.getPatientName());
            }
        });
        List<SelectedSessionServiceLine> serviceLines = new ArrayList<>();
        Patient patient = findPatientUseCase.findById(patientId);
        List<PatientSubmittedClaim> claims = patientSubmittedClaimRepository.findBySubmissionId(submissionId);
        claims.forEach(patientSubmittedClaim -> {
            PatientSession patientSession = mapper.map(patientSubmittedClaim.getPatientSession(), PatientSession.class);
            for (ServiceLine serviceLine : patientSubmittedClaim.getServiceLine()) {
                SelectedSessionServiceLine sessionServiceLine = new SelectedSessionServiceLine();
                sessionServiceLine.setSessionId(patientSession);
                sessionServiceLine.setServiceLine(serviceLine);
                serviceLines.add(sessionServiceLine);
            }
        });
        return ResendClaim.builder()
                .serviceLines(serviceLines)
                .patient(patient)
                .build();
    }
}
