package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.ServiceLinePaymentType;
import com.cob.billing.model.bill.posting.balance.ClientBalance;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.model.response.ClientBalanceResponse;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.usecases.bill.posting.FindSessionPaymentUseCase;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindClientPendingServiceLinesUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    FindSessionPaymentUseCase findSessionPaymentUseCase;

    public ClientBalanceResponse find(int offset, int limit, Long patientId, PatientSessionSearchCriteria patientSessionSearchCriteria) {
        List<ClientBalance> clientStatements = new ArrayList<>();
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findClientPendingSessions(patientId
                , patientSessionSearchCriteria.getStartDate()
                , patientSessionSearchCriteria.getEndDate());

        patientSessionEntities.stream()
                .forEach(patientSession -> {
                    List<Long> serviceLinesIds = patientSession.getServiceCodes().stream()
                            .map(PatientSessionServiceLineEntity::getId)
                            .collect(Collectors.toList());
                    List<SessionServiceLinePayment> payments = findSessionPaymentUseCase.find(serviceLinesIds);
                    List<ClientBalance> clientStatement = createClientStatement(patientSession.getServiceCodes(), payments, patientSession);
                    clientStatements.addAll(clientStatement);
                });

        List<ClientBalance> records = PaginationUtil.paginate(clientStatements, offset, limit);
        return ClientBalanceResponse.builder()
                .number_of_records(clientStatements.size())
                .number_of_matching_records(records.size())
                .records(records) //records
                .build();
    }

    private List<ClientBalance> createClientStatement(List<PatientSessionServiceLineEntity> serviceLines, List<SessionServiceLinePayment> payments, PatientSessionEntity session) {
        List<ClientBalance> clientStatement = new ArrayList<>();
        serviceLines.forEach(serviceLine -> {
            SessionServiceLinePayment sessionServiceLinePayment = findMatchPayment(payments, serviceLine.getId());
            ClientBalance clientBalance = createClientBalance(sessionServiceLinePayment, serviceLine, session);
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

    private ClientBalance createClientBalance(SessionServiceLinePayment payment, PatientSessionServiceLineEntity serviceLine, PatientSessionEntity session) {
        return ClientBalance.builder()
                .dos(session.getServiceDate())
                .serviceCode(serviceLine.getCptCode().getServiceCode() + '.' + serviceLine.getCptCode().getModifier())
                .provider(session.getDoctorInfo().getDoctorLastName() + ',' + session.getDoctorInfo().getDoctorFirstName())
                .charge(serviceLine.getCptCode().getCharge())
                .insCompanyPayment((payment != null && payment.getServiceLinePaymentType() != null && payment.getServiceLinePaymentType().equals(ServiceLinePaymentType.InsuranceCompany)) ? payment.getPayment() : 0)
                .clientPayment((payment != null && payment.getServiceLinePaymentType() != null && payment.getServiceLinePaymentType().equals(ServiceLinePaymentType.Client)) ? payment.getPayment() : 0)
                .adjustPayment(payment != null ? payment.getAdjust() : 0.0)
                .balance(payment != null ? payment.getBalance() : 0.0)
                .build();
    }
}
