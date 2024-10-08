package com.cob.billing.usecases.bill.invoice.cms.creator.multiple;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.ServiceLineExceedChunkChecker;
import com.cob.billing.usecases.bill.invoice.cms.creator.CreateCMSBoxesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateCMSPatientCaseUseCase {
    @Autowired
    CreateCMSBoxesUseCase createCMSBoxesUseCase;
    public Map<String,List<SelectedSessionServiceLine>> create(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException {
        List<String> fileNames = new ArrayList<>();
        Map<String,List<SelectedSessionServiceLine>> result = new HashMap<>();
        for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : patientCases(invoiceRequest).entrySet()) {
            List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(entry.getValue());
            for (int i = 0; i < serviceLinesChunks.size(); i++) {
                List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
                String fileName = "case_" + entry.getKey() + "_" + i;
               // createCMSBoxesUseCase.create(invoiceRequest, fileName, invoicesChunk);
                fileNames.add(fileName);
                result.put(fileName,invoicesChunk);
            }
        }
        return result;
    }
    private Map<String, List<SelectedSessionServiceLine>> patientCases(InvoiceRequest invoiceRequest){
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getCaseTitle()));

    }
}
