package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.claim.BillingClaim;
import com.cob.billing.usecases.bill.invoice.claim.ClaimCreator;
import com.cob.billing.usecases.clinical.patient.auth.CheckAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Component
public class GenerateClaimUseCase {
    @Autowired
    CheckModifierRuleUseCase checkModifierRuleUseCase;
    @Autowired
    CheckAuthorizationUseCase checkAuthorizationUseCase;
    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;

    public void generate(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException, IllegalAccessException, AuthorizationException, InvocationTargetException, NoSuchMethodException {
        checkModifierRuleUseCase.check(invoiceRequest);
        checkAuthorizationUseCase.check(invoiceRequest);
        createInvoiceRecordUseCase.createRecord(invoiceRequest);
        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());
        invoiceRequest.setResponse(response);
        BillingClaim billingClaim = ClaimCreator.getInstance(invoiceRequest.getSubmissionType());
        billingClaim.submit(invoiceRequest);
    }
}
