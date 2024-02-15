package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyExternalEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInvoiceExternalCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInvoiceInternalCompanyEntity;
import com.cob.billing.entity.clinical.patient.PatientEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.enums.DateServiceClaim;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequest;
import com.cob.billing.model.bill.invoice.tmp.InvoiceRequestConfiguration;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.bill.invoice.PatientInvoiceRepository;
import com.cob.billing.repositories.clinical.PatientRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceExternalCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceInternalCompanyRepository;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CreateInvoiceRecordUseCase {
    @Autowired
    private ServiceLineRepository serviceLineRepository;
    @Autowired
    private PatientSessionRepository patientSessionRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    private InsuranceCompanyExternalRepository insuranceCompanyExternalRepository;
    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    private PatientInvoiceInternalCompanyRepository patientInvoiceInternalCompanyRepository;
    @Autowired
    private PatientInvoiceExternalCompanyRepository patientInvoiceExternalCompanyRepository;
    @Autowired
    ModelMapper mapper;

    public List<PatientInvoiceEntity> createRecord(InvoiceRequest invoiceRequest) {
        List<PatientInvoiceEntity> toBeCreated = new ArrayList<>();
        PatientEntity patient = patientRepository.findById(invoiceRequest.getPatientInformation().getId()).get();
        invoiceRequest.getSelectedSessionServiceLine()
                .forEach(serviceLine -> {
                    PatientInvoiceEntity patientInvoice = new PatientInvoiceEntity();
                    patientInvoice.setPatient(patient);
                    patientInvoice.setDelayedReason(invoiceRequest.getInvoiceRequestConfiguration().getDelayedReason());
                    patientInvoice.setIsOneDateServicePerClaim(invoiceRequest.getInvoiceRequestConfiguration().getIsOneDateServicePerClaim());
                    patientInvoice.setServiceLine(mapper.map(serviceLine.getServiceLine(), PatientSessionServiceLineEntity.class));
                    patientInvoice.setPatientSession(mapper.map(serviceLine.getSessionId(), PatientSessionEntity.class));
                    toBeCreated.add(patientInvoice);
                });
        List<PatientInvoiceEntity> patientInvoiceRecords = StreamSupport.stream(patientInvoiceRepository.saveAll(toBeCreated).spliterator(), false)
                .collect(Collectors.toList());
        mapCreatedRecordToProperInsuranceCompany(invoiceRequest.getInvoiceInsuranceCompanyInformation().getVisibility(),
                invoiceRequest.getInvoiceInsuranceCompanyInformation().getName(), patientInvoiceRecords);
        return patientInvoiceRecords;
    }

    private void mapCreatedRecordToProperInsuranceCompany(InsuranceCompanyVisibility visibility, String insuranceCompanyName, List<PatientInvoiceEntity> patientInvoiceEntities) {
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
