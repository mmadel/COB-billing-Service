package com.cob.billing.usecases.bill.invoice.electronic.creator.multiple;


import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.request.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
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
public class CreateElectronicProviderUseCase {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;

    public List<Claim> create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        for (Map.Entry<DoctorInfo, List<SelectedSessionServiceLine>> entry : getProviders(invoiceRequest).entrySet()) {
            DoctorInfo provider = entry.getKey();
           // claims.add(createElectronicFieldsUseCase.create(invoiceRequest,provider,null,null));
        }
        return claims;
    }
    private Map<DoctorInfo, List<SelectedSessionServiceLine>> getProviders(InvoiceRequest invoiceRequest){
        return invoiceRequest.getSelectedSessionServiceLine().stream()
                .collect(Collectors.groupingBy(serviceLine -> serviceLine.getSessionId().getDoctorInfo()));
    }
}
