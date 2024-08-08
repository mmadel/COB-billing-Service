package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.InvoiceGenerationResponse;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.bill.invoice.CheckModifierRuleUseCase;
import com.cob.billing.usecases.clinical.patient.auth.CheckAuthorizationUseCase;
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

    @Autowired
    CheckAuthorizationUseCase checkAuthorizationUseCase;

    @Autowired
    CheckModifierRuleUseCase checkModifierRuleUseCase;
    @Transactional
    public InvoiceGenerationResponse generate(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, AuthorizationException {
        checkModifierRuleUseCase.check(invoiceRequest);
        checkAuthorizationUseCase.check(invoiceRequest);
        List<Long> records = createInvoiceRecordUseCase.createRecord(invoiceRequest);
        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());
        List<String> files = createCMSDocumentUseCase.createCMSDocument(invoiceRequest);
        return InvoiceGenerationResponse.builder()
                .files(files)
                .records(records)
                .build();
    }

}
