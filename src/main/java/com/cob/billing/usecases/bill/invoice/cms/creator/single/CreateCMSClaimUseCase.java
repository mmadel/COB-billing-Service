package com.cob.billing.usecases.bill.invoice.cms.creator.single;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.usecases.bill.invoice.cms.ServiceLineExceedChunkChecker;
import com.cob.billing.usecases.bill.invoice.cms.creator.CMSClaimCreator;
import com.cob.billing.usecases.bill.invoice.cms.creator.CreateCMSBoxesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("SingleClaimItems")
public class CreateCMSClaimUseCase implements CMSClaimCreator {
    @Autowired
    CreateCMSBoxesUseCase createCMSBoxesUseCase;
    public List<String> create(InvoiceRequest invoiceRequest,Boolean[] flags,Map<PatientSession, List<SelectedSessionServiceLine>> testMap) throws IOException, IllegalAccessException {
        List<String> fileNames = new ArrayList<>();
        for (Map.Entry<PatientSession, List<SelectedSessionServiceLine>> entry  : testMap.entrySet()) {
            List<List<SelectedSessionServiceLine>> serviceLinesChunks = ServiceLineExceedChunkChecker.check(entry.getValue());
            for (int i = 0; i < serviceLinesChunks.size(); i++) {
                List<SelectedSessionServiceLine> invoicesChunk = serviceLinesChunks.get(i);
                String fileName = "claim.pdf" + "_" + i+"_session_"+entry.getKey().getId();
                createCMSBoxesUseCase.create(invoiceRequest, fileName, invoicesChunk, entry.getKey());
                fileNames.add(fileName);
            }
        }

        return fileNames;
    }
}
