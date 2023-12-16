package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.response.PatientResponse;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindNotSubmittedPatientSessionUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper mapper;

    public PatientResponse findNotSubmittedSession(Pageable paging) {
        Page<PatientEntity> pages = patientRepository.findBySessionNotSubmittedByPatient(paging);
        long total = (pages).getTotalElements();
        List<Patient> records = pages.stream()
                .map(patientEntity -> {
                    Patient patient = mapper.map(patientEntity, Patient.class);
                    removeNotIInitialServiceCode(patient.getSessions());
                    return patient;
                })
                .collect(Collectors.toList());

        return PatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();
    }

    public Patient findNotSubmittedSessionByPatient(Long patientId) {
        PatientEntity patientEntity = patientRepository.findBySessionStatusByPatient(patientId);
        if (patientEntity != null) {
            Patient patient = mapper.map(patientEntity, Patient.class);
            removeNotIInitialServiceCode(patient.getSessions());
            return patient;
        } else
            return null;
    }

    private void removeNotIInitialServiceCode(List<PatientSession> sessions) {
        sessions.stream()
                .forEach(patientSession -> {
                    List<ServiceLine> toBeRemoved = new ArrayList<>();
                    for (ServiceLine serviceCode : patientSession.getServiceCodes()) {
                        if (serviceCode.getType().equals("Invoice") || serviceCode.getType().equals("Payment_Adjustment")) {
                            toBeRemoved.add(serviceCode);
                        }
                    }
                    patientSession.getServiceCodes().removeAll(toBeRemoved);
                });
    }
}
