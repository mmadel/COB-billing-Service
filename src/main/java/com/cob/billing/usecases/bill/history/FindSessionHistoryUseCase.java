package com.cob.billing.usecases.bill.history;

import com.cob.billing.entity.bill.invoice.PatientInvoiceDetailsEntity;
import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.SubmissionStatus;
import com.cob.billing.enums.SubmissionType;
import com.cob.billing.model.clinical.patient.Patient;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.model.history.SessionHistory;
import com.cob.billing.model.history.SessionHistoryCount;
import com.cob.billing.model.response.SessionHistoryResponse;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class FindSessionHistoryUseCase {
    //@Autowired
    PatientInvoiceRepository patientInvoiceRepository;
    //@Autowired
    MapSessionHistoryUseCase mapSessionHistoryUseCase;

    public SessionHistoryResponse find(Pageable paging) {
        List<SessionHistory> result;
        Page<PatientInvoiceEntity> pages = patientInvoiceRepository.findAll(paging);
        List<PatientInvoiceEntity> invoiceEntities = pages.getContent();
        long total = (pages).getTotalElements();
        invoiceEntities.stream()
                .filter(patientInvoice -> patientInvoice.getSubmissionId() != null)
                .collect(Collectors.toList());
        // Submissions
        result = mapSessionHistoryUseCase.map(invoiceEntities);
        return SessionHistoryResponse.builder()
                .number_of_records((int) total)
                .number_of_matching_records((int) invoiceEntities.size())
                .records(result)
                .build();
    }
}
