package com.cob.billing.usecases.clinical.patient.session;

import com.cob.billing.entity.clinical.patient.session.PatientSessionServiceLineEntity;
import com.cob.billing.model.clinical.patient.CPTCode;
import com.cob.billing.repositories.clinical.session.ServiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientSessionItemUseCase {
    @Autowired
    ServiceLineRepository serviceLineRepository;

    public Long update(Long id, CPTCode cptCode) {
        PatientSessionServiceLineEntity patientSessionServiceLineEntity = serviceLineRepository.findById(id).get();
        CPTCode code = patientSessionServiceLineEntity.getCptCode();
        if (cptCode.getServiceCode() != null){
            code.setServiceCode(cptCode.getServiceCode());
            code.setModifier(cptCode.getModifier());
        }
        if (cptCode.getUnit() != null)
            code.setUnit(cptCode.getUnit());
        if (cptCode.getCharge() != 0.0)
            code.setCharge(cptCode.getCharge());
        patientSessionServiceLineEntity.setCptCode(code);
        return serviceLineRepository.save(patientSessionServiceLineEntity).getId();
    }
}
