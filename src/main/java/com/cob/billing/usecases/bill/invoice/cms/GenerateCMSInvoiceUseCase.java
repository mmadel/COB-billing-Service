package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.InvoiceGenerationResponse;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.clinical.patient.PatientAuthorizationCheckerUseCase;
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
    PatientAuthorizationCheckerUseCase patientAuthorizationCheckerUseCase;

    @Transactional
    public InvoiceGenerationResponse generate(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, AuthorizationException {

        List<Long> records = createInvoiceRecordUseCase.createRecord(invoiceRequest);

        patientAuthorizationCheckerUseCase.check(invoiceRequest);

        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());

        List<String> files = createCMSDocumentUseCase.createCMSDocument(invoiceRequest);
        return InvoiceGenerationResponse.builder()
                .files(files)
                .records(records)
                .build();
    }

}
