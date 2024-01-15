package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyExternalEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInvoiceExternalCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInvoiceInternalCompanyEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.bill.invoice.InvoiceRequestCreation;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceExternalCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceInternalCompanyRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import com.cob.billing.usecases.bill.invoice.cms.CreateCMSDocumentUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateInvoiceUseCase {
    @Autowired
    ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    CreateCMSDocumentUseCase createCMSDocumentUseCase;
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientInvoiceExternalCompanyRepository patientInvoiceExternalCompanyRepository;
    @Autowired
    PatientInvoiceInternalCompanyRepository patientInvoiceInternalCompanyRepository;
    @Autowired
    InsuranceCompanyExternalRepository insuranceCompanyExternalRepository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    @Autowired
    CreateInvoiceRecordUseCase createInvoiceRecordUseCase;
    @Autowired
    MapInvoiceRecordUseCase mapInvoiceRecordUseCase;

    @Transactional
    public void create(InvoiceRequest invoiceRequest, HttpServletResponse response) throws IOException {
        createInvoiceRecordUseCase.createRecord(invoiceRequest.getSelectedSessionServiceLine()
                , invoiceRequest.getInvoiceRequestConfiguration(), invoiceRequest.getPatientInformation().getId());

        mapInvoiceRecordUseCase.mapRecord(invoiceRequest.getInvoiceInsuranceCompanyInformation().getVisibility()
                , invoiceRequest.getInvoiceInsuranceCompanyInformation().getName()
                , createInvoiceRecordUseCase.patientInvoiceRecords);
        changeSessionStatus(invoiceRequest.getSelectedSessionServiceLine());
        createCMSDocumentUseCase.create(invoiceRequest,response);
    }

    private void changeSessionStatus(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        selectedSessionServiceLines.stream()
                .forEach(serviceLine -> {
                    changeSessionStatusUseCase.change(serviceLine.getServiceLine());
                });
    }
}
