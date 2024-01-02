package com.cob.billing.usecases.bill.invoice.cms.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CMSDocumentModel {
    CarrierInformationModel carrierInformationModel;
    PatientInformationModel patientInformationModel;
    InsuredInformationModel insuredInformationModel;
    PhysicianInformationModel physicianInformationModel;
    SupplierInformationModel supplierInformationModel;
    List<ServiceLineModel> serviceLines;
}
