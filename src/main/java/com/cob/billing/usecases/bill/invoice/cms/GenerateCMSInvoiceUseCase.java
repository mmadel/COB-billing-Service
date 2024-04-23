package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.InvoiceGenerationResponse;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.model.clinical.patient.session.ServiceLine;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.bill.invoice.InvoiceFeeScheduleChargeUseCase;
import com.cob.billing.usecases.bill.invoice.InvoiceModifierRuleUseCase;
import com.cob.billing.usecases.clinical.patient.PatientAuthorizationCheckerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    InvoiceFeeScheduleChargeUseCase invoiceFeeScheduleChargeUseCase;
    @Autowired
    InvoiceModifierRuleUseCase invoiceModifierRuleUseCase;
    @Transactional
    public InvoiceGenerationResponse generate(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, AuthorizationException {
        List<CPTCode> cptCodes = invoiceRequest.getSelectedSessionServiceLine().stream()
                .map(serviceLine -> serviceLine.getServiceLine().getCptCode())
                .collect(Collectors.toList());
        invoiceFeeScheduleChargeUseCase.check(cptCodes, invoiceRequest.getInvoiceInsuranceCompanyInformation().getId());
        invoiceModifierRuleUseCase.check(cptCodes, invoiceRequest.getInvoiceInsuranceCompanyInformation().getId());
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
