package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
public class GenerateCMSInvoiceUseCase {

    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    CreateCMSDocumentUseCase createCMSDocumentUseCase;


    @Transactional
    public List<String> generate(InvoiceRequest invoiceRequest) throws IOException {

//        createInvoiceRecordUseCase.createRecord(invoiceRequest);
//
//        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());

        return createCMSDocumentUseCase.createCMSDocument(invoiceRequest);
    }

}
