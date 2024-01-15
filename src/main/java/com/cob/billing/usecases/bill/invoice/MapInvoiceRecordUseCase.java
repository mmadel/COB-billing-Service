package com.cob.billing.usecases.bill.invoice;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.InsuranceCompanyExternalEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInvoiceExternalCompanyEntity;
import com.cob.billing.entity.clinical.insurance.compnay.PatientInvoiceInternalCompanyEntity;
import com.cob.billing.model.clinical.insurance.company.InsuranceCompanyVisibility;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyExternalRepository;
import com.cob.billing.repositories.clinical.insurance.company.InsuranceCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceExternalCompanyRepository;
import com.cob.billing.repositories.clinical.insurance.company.PatientInvoiceInternalCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapInvoiceRecordUseCase {
    @Autowired
    private PatientInvoiceExternalCompanyRepository patientInvoiceExternalCompanyRepository;
    @Autowired
    private InsuranceCompanyExternalRepository insuranceCompanyExternalRepository;
    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    private PatientInvoiceInternalCompanyRepository patientInvoiceInternalCompanyRepository;

    public void mapRecord(InsuranceCompanyVisibility visibility, String insuranceCompanyName, List<PatientInvoiceEntity> patientInvoiceEntities) {
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
