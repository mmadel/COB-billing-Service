package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.ServiceLinePaymentType;
import com.cob.billing.model.admin.clinic.ClinicData;
import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.model.common.Address;
import com.cob.billing.usecases.bill.posting.FindSessionPaymentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConstructClientBalanceStatementsUseCase {
    @Autowired
    private FindSessionPaymentUseCase findSessionPaymentUseCase;

    public List<ClientBalancePayment> constructStatement(List<PatientSessionEntity> patientSessionEntities) {
        List<ClientBalancePayment> clientBalancePayments = new ArrayList<>();
        patientSessionEntities.stream()
                .forEach(patientSession -> {
                    List<Long> serviceLinesIds = patientSession.getServiceCodes().stream()
                            .map(PatientSessionServiceLineEntity::getId)
                            .collect(Collectors.toList());
                    List<SessionServiceLinePayment> payments = findSessionPaymentUseCase.find(serviceLinesIds);
                    List<ClientBalancePayment> clientStatement = createClientPayments(patientSession.getServiceCodes(), payments, patientSession);
                    clientBalancePayments.addAll(clientStatement);
                });

        return clientBalancePayments;

    }

    private List<ClientBalancePayment> createClientPayments(List<PatientSessionServiceLineEntity> serviceLines, List<SessionServiceLinePayment> payments, PatientSessionEntity session) {
        List<ClientBalancePayment> clientStatement = new ArrayList<>();
        serviceLines.forEach(serviceLine -> {
            SessionServiceLinePayment sessionServiceLinePayment = findMatchPayment(payments, serviceLine.getId());
            ClientBalancePayment clientBalance = createClientBalancePayment(sessionServiceLinePayment, serviceLine, session);
            clientStatement.add(clientBalance);
        });
        return clientStatement;
    }

    private SessionServiceLinePayment findMatchPayment(List<SessionServiceLinePayment> payments, Long serviceLineId) {
        SessionServiceLinePayment matchPayment = null;
        for (SessionServiceLinePayment payment : payments) {
            if (payment.getServiceLineId().equals(serviceLineId)) {
                matchPayment = payment;
                break;
            }
        }
        return matchPayment;
    }

    private ClientBalancePayment createClientBalancePayment(SessionServiceLinePayment payment, PatientSessionServiceLineEntity serviceLine, PatientSessionEntity session) {
        return ClientBalancePayment.builder()
                .dos(session.getServiceDate())
                .serviceCode(serviceLine.getCptCode().getServiceCode() + '.' + serviceLine.getCptCode().getModifier())
                .provider(session.getDoctorInfo().getDoctorLastName() + ',' + session.getDoctorInfo().getDoctorFirstName())
                .charge(serviceLine.getCptCode().getCharge())
                .insCompanyPayment((payment != null && payment.getServiceLinePaymentType() != null && payment.getServiceLinePaymentType().equals(ServiceLinePaymentType.InsuranceCompany)) ? payment.getPayment() : 0)
                .clientPayment((payment != null && payment.getServiceLinePaymentType() != null && payment.getServiceLinePaymentType().equals(ServiceLinePaymentType.Client)) ? payment.getPayment() : 0)
                .adjustPayment(payment != null ? payment.getAdjust() : 0.0)
                .balance(payment != null ? payment.getBalance() : 0.0)
                .units(serviceLine.getCptCode().getUnit())
                .placeOfCode(session.getPlaceOfCode())
                .clientBalanceAccount(createClientBalanceAccount(session, serviceLine.getId()))
                .sessionId(session.getId())
                .build();
    }

    private ClientBalanceAccount createClientBalanceAccount(PatientSessionEntity patientSession, Long serviceLineId) {
        return ClientBalanceAccount.builder()
                .facilityName(patientSession.getClinic().getTitle())
                .facilityAddress(getFacilityAddress(patientSession.getClinic().getClinicdata()))
                .caseTitle(patientSession.getCaseTitle())
                .icdCodes(patientSession.getServiceCodes().stream()
                        .map(serviceLine -> String.join(",", serviceLine.getDiagnoses())).findFirst().get())
                .clientName(patientSession.getPatient().getLastName() + ',' + patientSession.getPatient().getFirstName())
                .clientAddress(getClientAddress(patientSession.getPatient().getAddress()))
                .provider(patientSession.getDoctorInfo().getDoctorLastName()+','+patientSession.getDoctorInfo().getDoctorFirstName())
                .providerNPI(patientSession.getDoctorInfo().getDoctorNPI())
                .providerLicenseNumber("3033303")
                .sessionId(patientSession.getId())
                .serviceLineId(serviceLineId)
                .build();
    }

    private String getFacilityAddress(ClinicData clinicData) {
        return clinicData.getAddress() + ',' + clinicData.getCity() + ',' + clinicData.getState() + ',' + clinicData.getZipCode();
    }

    private String getClientAddress(Address address) {
        return address.getFirst() + ',' + address.getCity() + ',' + address.getState()
                + ",\n" + address.getCity() + ',' + address.getState() + ',' + address.getZipCode();
    }
}
