package com.cob.billing.usecases.bill.posting.batching;

import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.model.bill.posting.paymnet.batch.pdf.ClientBatchReceiptLocationInfo;
import com.cob.billing.model.bill.posting.paymnet.batch.pdf.ClientBatchReceiptPatientInfo;
import com.cob.billing.model.bill.posting.paymnet.batch.pdf.ClientBatchReceiptPaymentInfo;
import com.cob.billing.model.bill.posting.paymnet.batch.pdf.ClientBatchReceiptRequest;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.bill.posting.CreateSessionServiceLinePaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateBatchClientPaymentUseCase {
    @Autowired
    CreateSessionServiceLinePaymentUseCase createSessionServiceLinePaymentUseCase;
    @Autowired
    PatientSessionRepository patientSessionRepository;

    @Transactional
    public ClientBatchReceiptRequest create(ServiceLinePaymentRequest serviceLinePaymentRequest) {
        ClientBatchReceiptRequest model = new ClientBatchReceiptRequest();
        //createSessionServiceLinePaymentUseCase.create(serviceLinePaymentRequest);

        Map<Long, List<SessionServiceLinePayment>> paymentsGroupedBySession = serviceLinePaymentRequest.getServiceLinePayments()
                .stream()
                .collect(Collectors.groupingBy(SessionServiceLinePayment::getSessionId));


        List<PatientSessionEntity> sessions = StreamSupport
                .stream(patientSessionRepository.findAllById(paymentsGroupedBySession.keySet()).spliterator(), false)
                .collect(Collectors.toList());


        fillClientInfo(sessions.stream().findFirst().get().getPatient(), model);

        fillPaymentInfo(serviceLinePaymentRequest.getReceivedDate(),
                serviceLinePaymentRequest.getPaymentMethod(),
                serviceLinePaymentRequest.getTotalAmount(),
                model);

        fillLocation(sessions, model);
        return model;
    }

    private void fillClientInfo(PatientEntity patient, ClientBatchReceiptRequest model) {
        ClientBatchReceiptPatientInfo clientBatchReceiptPatientInfo = new ClientBatchReceiptPatientInfo();
        clientBatchReceiptPatientInfo.setPatientName(patient.getLastName() + "," + patient.getFirstName());
        clientBatchReceiptPatientInfo.setAddress(patient.getAddress().getFirst());
        clientBatchReceiptPatientInfo.setCity(patient.getAddress().getCity());
        clientBatchReceiptPatientInfo.setState(patient.getAddress().getState());
        clientBatchReceiptPatientInfo.setZipCode(patient.getAddress().getZipCode());
        model.setClientBatchReceiptPatientInfo(clientBatchReceiptPatientInfo);

    }

    private void fillPaymentInfo(Long receivedDate, String paymentMethod, Long totalAmount, ClientBatchReceiptRequest model) {
        ClientBatchReceiptPaymentInfo clientBatchReceiptPaymentInfo = new ClientBatchReceiptPaymentInfo();
        clientBatchReceiptPaymentInfo.setReceivedDate(receivedDate);
        clientBatchReceiptPaymentInfo.setPaymentMethod(paymentMethod);
        clientBatchReceiptPaymentInfo.setTotalPayment(totalAmount.floatValue());
        model.setClientBatchReceiptPaymentInfo(clientBatchReceiptPaymentInfo);
    }

    private void fillLocation(List<PatientSessionEntity> sessions, ClientBatchReceiptRequest model) {
        List<ClientBatchReceiptLocationInfo> clientBatchReceiptLocationInfo = new ArrayList<>();
        List<PatientSessionEntity> sessionsDistinct = sessions.stream()
                .collect(Collectors.toMap(
                        session -> session.getClinic().getId(),  // Key is the clinic id
                        session -> session,                     // Value is the session entity
                        (existing, replacement) -> existing     // If duplicates, keep the existing session entity
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
        for (PatientSessionEntity session : sessionsDistinct) {
            ClientBatchReceiptLocationInfo locationInfo = new ClientBatchReceiptLocationInfo();
            locationInfo.setLocationName(session.getClinic().getTitle());
            String locationAddress = session.getClinic().getClinicdata().getAddress()
                    + "," + session.getClinic().getClinicdata().getCity()
                    + "," + session.getClinic().getClinicdata().getState()
                    + "," + session.getClinic().getClinicdata().getZipCode();
            locationInfo.setLocationAddress(locationAddress);
            clientBatchReceiptLocationInfo.add(locationInfo);
        }
        model.setClientBatchReceiptLocationInfo(clientBatchReceiptLocationInfo);
    }
}
