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


    @Transactional
    public void create(InvoiceRequestCreation invoiceRequestCreation, HttpServletResponse response) throws IOException {
        List<PatientInvoiceEntity> toBeCreated = new ArrayList<>();
        PatientEntity patient = patientRepository.findById(invoiceRequestCreation.getPatientId()).get();
        invoiceRequestCreation.getSelectedSessionServiceLines().stream()
                .forEach(serviceLine -> {
                    PatientInvoiceEntity patientInvoice = new PatientInvoiceEntity();
                    patientInvoice.setPatient(patient);
                    patientInvoice.setDelayedReason(invoiceRequestCreation.getDelayedReason());
                    patientInvoice.setIsOneDateServicePerClaim(invoiceRequestCreation.getIsOneDateServicePerClaim());
                    patientInvoice.setServiceLine(serviceLineRepository.findById(serviceLine.getServiceLine()).get());
                    patientInvoice.setPatientSession(patientSessionRepository.findById(serviceLine.getSessionId()).get());
                    toBeCreated.add(patientInvoice);
                });

        List<PatientInvoiceEntity> created = StreamSupport.stream(patientInvoiceRepository.saveAll(toBeCreated).spliterator(), false)
                .collect(Collectors.toList());
        mapPatientInvoiceToInsuranceCompany(invoiceRequestCreation.getVisibility(), invoiceRequestCreation.getInsuranceCompany()[0], created);
        Object[] insuranceCompany = {invoiceRequestCreation.getInsuranceCompany()[0], invoiceRequestCreation.getVisibility()};
        createCMSDocumentUseCase.create(created, patient,insuranceCompany, response);
        changeSessionStatus(invoiceRequestCreation.getSelectedSessionServiceLines());
    }

    private void changeSessionStatus(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        selectedSessionServiceLines.stream()
                .forEach(serviceLine -> {
                    changeSessionStatusUseCase.change(serviceLine.getServiceLine());
                });
    }

    private void mapPatientInvoiceToInsuranceCompany(InsuranceCompanyVisibility visibility, String insuranceCompanyName, List<PatientInvoiceEntity> patientInvoiceEntities) {
        switch (visibility) {
            case External:
                InsuranceCompanyExternalEntity insuranceCompanyExternal = insuranceCompanyExternalRepository.findByInsuranceCompanyName(insuranceCompanyName)
                        .orElseThrow(() -> new IllegalArgumentException());
                List<PatientInvoiceExternalCompanyEntity> patientInvoiceExternalCompanyEntities = new ArrayList<>();
                patientInvoiceEntities.stream()
                        .forEach(patientInvoice -> {
                            PatientInvoiceExternalCompanyEntity patientInvoiceExternalCompany = new PatientInvoiceExternalCompanyEntity();
                            patientInvoiceExternalCompany.setExternalPatientInvoice(patientInvoice);
                            patientInvoiceExternalCompany.setExternalInsuranceCompany(insuranceCompanyExternal);
                            patientInvoiceExternalCompanyEntities.add(patientInvoiceExternalCompany);
                        });
                patientInvoiceExternalCompanyRepository.saveAll(patientInvoiceExternalCompanyEntities);
                break;
            case Internal:
                InsuranceCompanyEntity insuranceCompany = insuranceCompanyRepository.findByInsuranceCompanyName(insuranceCompanyName)
                        .orElseThrow(() -> new IllegalArgumentException());
                List<PatientInvoiceInternalCompanyEntity> patientInvoiceInternalCompanyEntities = new ArrayList<>();
                patientInvoiceEntities.stream()
                        .forEach(patientInvoice -> {
                            PatientInvoiceInternalCompanyEntity patientInvoiceInternalCompany = new PatientInvoiceInternalCompanyEntity();
                            patientInvoiceInternalCompany.setInternalPatientInvoice(patientInvoice);
                            patientInvoiceInternalCompany.setInternalInsuranceCompany(insuranceCompany);
                            patientInvoiceInternalCompanyEntities.add(patientInvoiceInternalCompany);
                        });
                patientInvoiceInternalCompanyRepository.saveAll(patientInvoiceInternalCompanyEntities);
                break;
        }

    }
}
