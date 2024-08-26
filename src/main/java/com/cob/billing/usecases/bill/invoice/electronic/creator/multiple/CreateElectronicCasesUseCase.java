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
public class CreateElectronicCasesUseCase {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;

    public List<Claim> create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        for (Map.Entry<String, List<SelectedSessionServiceLine>> entry : getPatientCases(invoiceRequest).entrySet()) {
            claims.add(createElectronicFieldsUseCase.create(invoiceRequest,null,null,entry.getValue()));
        }
        return claims;
    }
    public Map<String, List<SelectedSessionServiceLine>> getPatientCases(InvoiceRequest invoiceRequest) {
        return
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getCaseTitle()));
    }
}
