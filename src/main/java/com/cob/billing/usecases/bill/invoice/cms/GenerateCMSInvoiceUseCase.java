package com.cob.billing.usecases.bill.invoice.cms;

import com.cob.billing.exception.business.AuthorizationException;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.usecases.bill.invoice.ChangeSessionStatusUseCase;
import com.cob.billing.usecases.bill.invoice.CheckModifierRuleUseCase;
import com.cob.billing.usecases.bill.invoice.CreateInvoiceRecordUseCase;
import com.cob.billing.usecases.bill.invoice.provider.ClaimProvider;
import com.cob.billing.usecases.bill.invoice.provider.CreateClaimProvider;
import com.cob.billing.usecases.clinical.patient.auth.CheckAuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GenerateCMSInvoiceUseCase {

    @Autowired
    private CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    private ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    CheckAuthorizationUseCase checkAuthorizationUseCase;

    @Autowired
    CheckModifierRuleUseCase checkModifierRuleUseCase;
    @Autowired
    UploadCMSFileUseCase uploadCMSFileUseCase;
    @Transactional
    public void generate(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException, IllegalAccessException, AuthorizationException {
        checkModifierRuleUseCase.check(invoiceRequest);
        checkAuthorizationUseCase.check(invoiceRequest);
        createInvoiceRecordUseCase.createRecord(invoiceRequest);
        changeSessionStatusUseCase.change(invoiceRequest.getSelectedSessionServiceLine());
        invoiceRequest.setResponse(response);
        ClaimProvider claimProvider= CreateClaimProvider.getInstance(invoiceRequest);
        claimProvider.submit();
    }

}
