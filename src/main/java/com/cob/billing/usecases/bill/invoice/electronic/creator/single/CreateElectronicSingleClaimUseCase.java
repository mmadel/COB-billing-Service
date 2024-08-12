package com.cob.billing.usecases.bill.invoice.electronic.creator.single;

import com.cob.billing.model.admin.clinic.Clinic;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.patient.session.DoctorInfo;
import com.cob.billing.model.integration.claimmd.Claim;
import com.cob.billing.usecases.bill.invoice.FindClinicAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.FindProviderAssignedToServiceLinesUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.CreateElectronicFieldsUseCase;
import com.cob.billing.usecases.bill.invoice.electronic.creator.ElectronicClaimCreator;
import com.cob.billing.usecases.bill.invoice.electronic.filler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Qualifier("SingleElectronicClaim")
public class CreateElectronicSingleClaimUseCase implements ElectronicClaimCreator {
    @Autowired
    CreateElectronicFieldsUseCase createElectronicFieldsUseCase;
    @Override
    public List<Claim> create(InvoiceRequest invoiceRequest, Boolean[] flags) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return Arrays.asList(createElectronicFieldsUseCase.create(invoiceRequest));
    }
}
