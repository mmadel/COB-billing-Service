package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.claim.BillingClaim;
import com.cob.billing.usecases.bill.invoice.claim.ClaimCreator;
import com.cob.billing.usecases.clinical.patient.auth.CheckAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;

    @Autowired
    CreateCMSFileUseCase createCMSFileUseCase;
    @Autowired
    CreatPatientInvoiceRecordUseCase creatPatientInvoiceRecordUseCase;

    @Transactional
    public void generate(InvoiceRequest invoiceRequest) throws IOException, IllegalAccessException, AuthorizationException, InvocationTargetException, NoSuchMethodException {
        checkModifierRuleUseCase.check(invoiceRequest);
        checkAuthorizationUseCase.check(invoiceRequest);
        BillingClaim billingClaim = ClaimCreator.getInstance(invoiceRequest.getSubmissionType());
        billingClaim.submit(invoiceRequest);
        creatPatientInvoiceRecordUseCase.create(invoiceRequest, billingClaim.getInvoiceResponse());
        //createInvoiceRecordUseCase.createRecord(invoiceRequest);
        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());
//        createPatientClaimUseCase.create(billingClaim.getInvoiceResponse());
        //createCMSFileUseCase.upload(invoiceRequest);
    }
}
