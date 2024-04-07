package com.cob.billing.usecases.bill.posting;

import com.cob.billing.entity.bill.payment.PatientSessionServiceLinePaymentEntity;
import com.cob.billing.entity.bill.payment.PatientSessionServiceLinePaymentInfoEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.posting.paymnet.SessionServiceLinePayment;
import com.cob.billing.model.bill.posting.paymnet.ServiceLinePaymentRequest;
import com.cob.billing.repositories.bill.posting.PatientSessionServiceLinePaymentInfoRepository;
import com.cob.billing.repositories.bill.posting.PatientSessionServiceLinePaymentRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateSessionServiceLinePaymentUseCase {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PatientSessionServiceLinePaymentRepository patientSessionServiceLinePaymentRepository;
    @Autowired
    private PatientSessionServiceLinePaymentInfoRepository patientSessionServiceLinePaymentDetailsEntityRepository;
    @Autowired
    private ServiceLineRepository serviceLineRepository;
    @Autowired
    UpdateServiceLinesStatusUseCase updateServiceLinesStatusUseCase;
    @Autowired
    UpdateSessionStatusUseCase updateSessionStatusUseCase;

    private Map<Long, PatientSessionServiceLineEntity> serviceLineCache;

    @Transactional
    public void create(ServiceLinePaymentRequest serviceLinePaymentRequest) {
     /*
        Save Service Line Payment and assign to it catching service line by its id
        Save Service Line Details and assign to it created service Lines
      */
        cacheServiceLines(serviceLinePaymentRequest.getServiceLinePayments());
        PatientSessionServiceLinePaymentInfoEntity paymentInfo = createPaymentInfo(serviceLinePaymentRequest);
        List<PatientSessionServiceLinePaymentEntity> entities = serviceLinePaymentRequest.getServiceLinePayments()
                .stream()
                .map(serviceLinePayment -> mapServiceLinePayment(serviceLinePayment, paymentInfo)).collect(Collectors.toList());
        patientSessionServiceLinePaymentRepository.saveAll(entities);

        updateServiceLinesStatusUseCase.update(serviceLinePaymentRequest.getServiceLinePayments());
        updateSessionStatusUseCase.update(serviceLinePaymentRequest.getServiceLinePayments());
    }

    private PatientSessionServiceLinePaymentEntity mapServiceLinePayment(SessionServiceLinePayment sessionServiceLinePayment, PatientSessionServiceLinePaymentInfoEntity paymentInfo) {

        PatientSessionServiceLinePaymentEntity patientSessionServiceLinePayment = mapper.map(sessionServiceLinePayment, PatientSessionServiceLinePaymentEntity.class);
        patientSessionServiceLinePayment.setServiceLine(serviceLineCache.get(sessionServiceLinePayment.getServiceLineId()));
        patientSessionServiceLinePayment.setPatientSessionServiceLinePaymentInfoEntity(paymentInfo);
        return patientSessionServiceLinePayment;
    }

    private void cacheServiceLines(List<SessionServiceLinePayment> sessionServiceLinePayments) {
        List<Long> serviceLinesIds = sessionServiceLinePayments.stream().map(serviceLinePayment -> serviceLinePayment.getServiceLineId())
                .collect(Collectors.toList());
        serviceLineCache = new HashMap();
        StreamSupport.stream(serviceLineRepository.findAllById(serviceLinesIds).spliterator(), false)
                .forEach(patientSessionServiceLineEntity -> {
                    serviceLineCache.put(patientSessionServiceLineEntity.getId(), patientSessionServiceLineEntity);
                });
    }

    private PatientSessionServiceLinePaymentInfoEntity createPaymentInfo(ServiceLinePaymentRequest serviceLinePaymentRequest) {
        PatientSessionServiceLinePaymentInfoEntity paymentInfo = mapper.map(serviceLinePaymentRequest, PatientSessionServiceLinePaymentInfoEntity.class);
        return patientSessionServiceLinePaymentDetailsEntityRepository.save(paymentInfo);
    }
}
