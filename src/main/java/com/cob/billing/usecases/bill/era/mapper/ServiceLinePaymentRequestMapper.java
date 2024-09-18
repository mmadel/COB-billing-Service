package com.cob.billing.usecases.bill.era.mapper;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.enums.ServiceLinePaymentAction;
import com.cob.billing.enums.ServiceLinePaymentType;
import com.cob.billing.model.bill.posting.era.ERADataTransferModel;
import com.cob.billing.model.bill.posting.era.ERADetailsLine;
import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ServiceLinePaymentRequestMapper {
    Map<PatientSessionEntity, Long> sessionToServiceLineMapper = new HashMap<>();
    @Autowired
    PatientSessionRepository patientSessionRepository;

    public ServiceLinePaymentRequest map(ERADataTransferModel era, List<ERADetailsLine> historyLines) {
        ServiceLinePaymentRequest serviceLinePaymentRequest = new ServiceLinePaymentRequest();
        serviceLinePaymentRequest.setServiceLinePaymentType(ServiceLinePaymentType.InsuranceCompany);
        serviceLinePaymentRequest.setTotalAmount(Double.valueOf(era.getPaidAmount()).longValue());
        serviceLinePaymentRequest.setPaymentMethod(era.getCheckType());
        serviceLinePaymentRequest.setReceivedDate(new Date().getTime());
        serviceLinePaymentRequest.setCheckDate(convertDate(era.getPaidDate()));
        serviceLinePaymentRequest.setCheckNumber(era.getCheckNumber());
        createPayments(serviceLinePaymentRequest, historyLines);
        return serviceLinePaymentRequest;

    }

    private Long convertDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        return localDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private void createPayments(ServiceLinePaymentRequest serviceLinePaymentRequest, List<ERADetailsLine> historyLines) {
        List<SessionServiceLinePayment> serviceLinePayments = new ArrayList<>();
        getSessionMap(historyLines.stream().map(eraLineHistory -> Long.parseLong(eraLineHistory.getServiceLineID()))
                .collect(Collectors.toList()));
        for (int i = 0; i < historyLines.size(); i++) {
            ERADetailsLine line = historyLines.get(i);
            double balance = line.getBillAmount() - (line.getAdjustAmount() + line.getPaidAmount());
            SessionServiceLinePayment payment = new SessionServiceLinePayment(balance, line.getPaidAmount()
                    , line.getAdjustAmount(), Long.parseLong(line.getServiceLineID()), new Date().getTime(), ServiceLinePaymentType.InsuranceCompany);
            payment.setServiceLinePaymentAction(ServiceLinePaymentAction.valueOf(line.getAction()));
            payment.setServiceLineId(Long.parseLong(line.getServiceLineID()));
            payment.setSessionId(sessionToServiceLineMapper.get(Long.parseLong(line.getServiceLineID())));
            serviceLinePayments.add(payment);
        }
        serviceLinePaymentRequest.setServiceLinePayments(serviceLinePayments);
    }

    private void getSessionMap(List<Long> serviceLines) {
        List<PatientSessionEntity> patientSessions = patientSessionRepository.findSessionByServiceLine(serviceLines).get();
        for (PatientSessionEntity session : patientSessions) {
            Long matchedServiceLine = session.getServiceCodes().stream()
                    .filter(sl -> serviceLines.contains(sl.getId()))
                    .map(serviceLine -> serviceLine.getId())
                    .findFirst()
                    .get();

            sessionToServiceLineMapper.put(session, matchedServiceLine);
        }
    }
}
