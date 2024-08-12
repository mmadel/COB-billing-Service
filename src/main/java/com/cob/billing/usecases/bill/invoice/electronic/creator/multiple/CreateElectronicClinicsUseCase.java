package com.cob.billing.usecases.bill.invoice.electronic.creator.multiple;


import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.FindProviderAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.CreateElectronicFieldsUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.filler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateElectronicClinicsUseCase {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;
    public List<Claim> create(InvoiceRequest invoiceRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Claim> claims = new ArrayList<>();
        for (Map.Entry<Clinic, List<SelectedSessionServiceLine>> entry : getClinics(invoiceRequest).entrySet()) {
            Clinic clinic = entry.getKey();
            claims.add(createElectronicFieldsUseCase.create(invoiceRequest,null,clinic,null));
        }
        return claims;
    }

    private Map<Clinic, List<SelectedSessionServiceLine>> getClinics(InvoiceRequest invoiceRequest){
        return
                invoiceRequest.getSelectedSessionServiceLine().stream()
                        .collect(Collectors.groupingBy(patientInvoice -> patientInvoice.getSessionId().getClinic()));
    }
}
