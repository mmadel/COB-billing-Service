package com.cob.billing.usecases.bill.invoice.cms.creator;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceLineExceedChunkChecker {
    static final int CHUNK_SIZE = 6;

    public static List<List<PatientInvoiceEntity>> check(List<PatientInvoiceEntity> patientInvoiceRecords) {
        return IntStream.range(0, (patientInvoiceRecords.size() + CHUNK_SIZE - 1) / CHUNK_SIZE)
                .mapToObj(i -> patientInvoiceRecords.subList(i * CHUNK_SIZE, Math.min((i + 1) * CHUNK_SIZE, patientInvoiceRecords.size())))
                .collect(Collectors.toList());
    }
}
