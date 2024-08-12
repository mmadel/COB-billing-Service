package com.cob.billing.usecases.bill.invoice.cms.creator.single;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.cms.ServiceLineExceedChunkChecker;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import com.cob.billing.usecases.bill.invoice.cms.creator.CreateCMSBoxesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("SingleClaimItems")
public class CreateCMSClaimUseCase implements CMSClaimCreator {
    @Autowired
    CreateCMSBoxesUseCase createCMSBoxesUseCase;
    public List<String> create(InvoiceRequest invoiceRequest,Boolean[] flags) throws IOException, IllegalAccessException {
        List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(invoiceRequest.getSelectedSessionServiceLine());
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < serviceLinesChunks.size(); i++) {
            List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
            String fileName = "claim.pdf" + "_" + i;
            createCMSBoxesUseCase.create(invoiceRequest, fileName, invoicesChunk);
            fileNames.add(fileName);
        }
        return fileNames;
    }
}
