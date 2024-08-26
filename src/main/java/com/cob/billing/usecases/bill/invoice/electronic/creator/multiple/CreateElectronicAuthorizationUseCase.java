package com.cob.billing.usecases.bill.invoice.electronic.creator.multiple;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.electronic.creator.CreateElectronicFieldsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateElectronicAuthorizationUseCase {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;

    public List<Claim> create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : getAuthorizations(invoiceRequest).entrySet()) {
            claims.add(createElectronicFieldsUseCase.create(invoiceRequest,null,null,entry.getValue()));
        }
        return claims;
    }

    private Map<String, List<SelectedSessionServiceLine>> getAuthorizations(InvoiceRequest invoiceRequest){
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .filter(serviceLine -> serviceLine.getSessionId().getAuthorizationNumber() != null)
                .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getAuthorizationNumber()));
    }
}
