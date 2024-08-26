package com.cob.billing.usecases.bill.invoice.cms.creator.multiple;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.ServiceLineExceedChunkChecker;
import com.cob.billing.usecases.bill.invoice.cms.creator.CreateCMSBoxesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateCMSDatesUseCase {
    @Autowired
    CreateCMSBoxesUseCase createCMSBoxesUseCase;
    public List<String> create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        List<String> fileNames = new ArrayList<>();
        for (Map.Entry<Long, List<SelectedSessionServiceLine>> entry : getDates(invoiceRequest).entrySet()) {
            List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(entry.getValue());
            for (int i = 0; i < serviceLinesChunks.size(); i++) {
                List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
                String fileName = "date_" + entry.getKey() + "_" + i;
                createCMSBoxesUseCase.create(invoiceRequest, fileName, invoicesChunk);
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }
    private Map<Long, List<SelectedSessionServiceLine>> getDates(InvoiceRequest invoiceRequest){
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getServiceDate()));
    }
}
