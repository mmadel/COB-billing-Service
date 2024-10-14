package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaim;
import com.cob.billing.entity.bill.invoice.submitted.PatientSubmittedClaimServiceLine;
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
            populateSubmittedPatientSession(patientSession, patientSubmittedClaim);
            for (PatientSubmittedClaimServiceLine patientSubmittedClaimServiceLine : patientSubmittedClaim.getServiceLine()) {
                SelectedSessionServiceLine sessionServiceLine = new SelectedSessionServiceLine();
                sessionServiceLine.setSessionId(patientSession);
                ServiceLine serviceLine = mapper.map(patientSubmittedClaimServiceLine, ServiceLine.class);
                serviceLine.setId(patientSubmittedClaimServiceLine.getServiceLineId());
                sessionServiceLine.setServiceLine(serviceLine);
                serviceLines.add(sessionServiceLine);
            }
        });
        return ResendClaim.builder()
                .serviceLines(serviceLines)
                .patient(patient)
                .build();
    }

    private void populateSubmittedPatientSession(PatientSession patientSession, PatientSubmittedClaim patientSubmittedClaim) {
        if (patientSubmittedClaim.getDateOfService() != null)
            patientSession.setServiceDate(patientSubmittedClaim.getDateOfService());
        if (patientSubmittedClaim.getServiceStartTime() != null)
            patientSession.setServiceStartTime(patientSubmittedClaim.getServiceStartTime());
        if (patientSubmittedClaim.getServiceEndTime() != null)
            patientSession.setServiceEndTime(patientSubmittedClaim.getServiceEndTime());
        if (patientSubmittedClaim.getPlaceOfCode() != null)
            patientSession.setPlaceOfCode(patientSubmittedClaim.getPlaceOfCode());
        if (patientSubmittedClaim.getProviderFirstName() != null)
            patientSession.getDoctorInfo().setDoctorFirstName(patientSubmittedClaim.getProviderFirstName());
        if (patientSubmittedClaim.getProviderLastName() != null)
            patientSession.getDoctorInfo().setDoctorLastName(patientSubmittedClaim.getProviderLastName());
        if (patientSubmittedClaim.getProvider_npi() != null)
            patientSession.getDoctorInfo().setDoctorNPI(patientSubmittedClaim.getProvider_npi());
        if (patientSubmittedClaim.getClinic() != null)
            patientSession.setClinic(patientSubmittedClaim.getClinic());
        if (patientSubmittedClaim.getCaseDiagnosis() != null)
            patientSession.setCaseDiagnosis(patientSubmittedClaim.getCaseDiagnosis());
    }
}
