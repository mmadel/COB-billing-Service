package com.cob.billing.usecases.bill.invoice.cms.checker;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceLineExceedChunkChecker {
    static final int CHUNK_SIZE = 6;

    public static List<List<SelectedSessionServiceLine>> check(List<SelectedSessionServiceLine> selectedSessionServiceLine) {
        return IntStream.range(0, (selectedSessionServiceLine.size() + CHUNK_SIZE - 1) / CHUNK_SIZE)
                .mapToObj(i -> selectedSessionServiceLine.subList(i * CHUNK_SIZE, Math.min((i + 1) * CHUNK_SIZE, selectedSessionServiceLine.size())))
                .collect(Collectors.toList());
    }
}
