package com.cob.billing.usecases.bill.invoice.electronic.creator.multiple;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.bill.invoice.request.InvoiceRequestConfiguration;
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
public class CreateElectronicDatesUseCase {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;

    public List<Claim> create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        for (Map.Entry<Long, List<SelectedSessionServiceLine>> entry : getDates(invoiceRequest).entrySet()) {
           // claims.add(createElectronicFieldsUseCase.create(invoiceRequest,null,null,entry.getValue()));
        }
        return claims;
    }

    private Map<Long, List<SelectedSessionServiceLine>> getDates(InvoiceRequest invoiceRequest){
        return
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getServiceDate()));
    }

    private boolean isDatePerClaim(InvoiceRequestConfiguration invoiceRequestConfiguration) {
        if (invoiceRequestConfiguration.getIsOneDateServicePerClaim() != null) {
            return invoiceRequestConfiguration.getIsOneDateServicePerClaim();
        } else {
            return false;
        }
    }
}
