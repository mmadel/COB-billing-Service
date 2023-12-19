package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.insurance.PatientInsuranceEntity;
import com.cob.billing.model.bill.invoice.InvoiceRequestCreation;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.repositories.bill.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateInvoiceUseCase {
    @Autowired
    ChangeSessionStatusUseCase changeSessionStatusUseCase;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    ModelMapper mapper;

    @Transactional
    public void create(InvoiceRequestCreation invoiceRequestCreation) {
        List<PatientInvoiceEntity> toBeCreated = new ArrayList<>();
        invoiceRequestCreation.getSelectedSessionServiceLines().stream()
                .forEach(serviceLine -> {
                    PatientInvoiceEntity patientInvoice = new PatientInvoiceEntity();
                    patientInvoice.setPatient(patientRepository.findById(invoiceRequestCreation.getPatientId()).get());
                    patientInvoice.setDelayedReason(invoiceRequestCreation.getDelayedReason());
                    patientInvoice.setIsOneDateServicePerClaim(invoiceRequestCreation.getIsOneDateServicePerClaim());
                    patientInvoice.setServiceLine(serviceLineRepository.findById(serviceLine.getServiceLine()).get());
                    patientInvoice.setPatientSession(patientSessionRepository.findById(serviceLine.getSessionId()).get());
                    patientInvoice.setInsuranceCompany(invoiceRequestCreation.getInsuranceCompanyId());
                    toBeCreated.add(patientInvoice);
                });
        patientInvoiceRepository.saveAll(toBeCreated);
        generateCMSDocument();
        changeSessionStatus(invoiceRequestCreation.getSelectedSessionServiceLines());
    }

    /*TODO
        generate CMS-1500 document , may be using lib or generate it from scratch
     */
    private void generateCMSDocument() {

    }

    private void changeSessionStatus(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        selectedSessionServiceLines.stream()
                .forEach(serviceLine -> {
                    changeSessionStatusUseCase.change(serviceLine.getServiceLine());
                });
    }
}
