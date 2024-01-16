package com.cob.billing.usecases.bill.invoice.cms.creators;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLineCMSDocumentCreator {
    public PdfAcroForm cmsForm;

    public void create(List<PatientInvoiceEntity> patientInvoices,PdfAcroForm cmsForm) {
        this.cmsForm = cmsForm;
        int counter = 1;
        double totalCharge = 0.0;
        DecimalFormat df = new DecimalFormat("#.00");
        for (PatientInvoiceEntity patientInvoice : patientInvoices) {
            String[] dateOfService = DateConstructor.construct(patientInvoice.getPatientSession().getServiceDate());
            cmsForm.getField("sv" + counter + "_mm_from").setValue(dateOfService[0]);
            cmsForm.getField("sv" + counter + "_dd_from").setValue(dateOfService[1]);
            cmsForm.getField("sv" + counter + "_yy_from").setValue(dateOfService[2]);

            cmsForm.getField("sv" + counter + "_mm_end").setValue(dateOfService[0]);
            cmsForm.getField("sv" + counter + "_dd_end").setValue(dateOfService[1]);
            cmsForm.getField("sv" + counter + "_yy_end").setValue(dateOfService[2]);
            cmsForm.getField("ch" + counter).setValue(String.valueOf(patientInvoice.getServiceLine().getCptCode().getCharge())
                    , df.format(patientInvoice.getServiceLine().getCptCode().getCharge()).replace(".", " "));
            cmsForm.getField("day" + counter).setValue(patientInvoice.getServiceLine().getCptCode().getUnit().toString());

            cmsForm.getField("place" + counter).setValue(patientInvoice.getPatientSession().getPlaceOfCode().split("_")[1]);
            cmsForm.getField("cpt" + counter).setValue(patientInvoice.getServiceLine().getCptCode().getServiceCode());
            if (patientInvoice.getServiceLine().getCptCode().getModifier().length() > 0) {
                String[] modList = patientInvoice.getServiceLine().getCptCode().getModifier().split("\\.");
                try {
                    cmsForm.getField("mod" + counter).setValue(modList[0]);
                    cmsForm.getField("mod" + counter + "a").setValue(modList[1]);
                    cmsForm.getField("mod" + counter + "b").setValue(modList[2]);
                    cmsForm.getField("mod" + counter + "c").setValue(modList[3]);
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
            }
            cmsForm.getField("local" + counter).setValue(patientInvoice.getPatientSession().getDoctorInfo().getDoctorNPI());
            counter = counter + 1;
            totalCharge = totalCharge + patientInvoice.getServiceLine().getCptCode().getCharge();
        }
        df.format(totalCharge);
        getSessionDiagnosis(patientInvoices);
        cmsForm.getField("t_charge").setValue(String.valueOf(totalCharge), df.format(totalCharge).replace(".", " "));
    }


    private void getSessionDiagnosis(List<PatientInvoiceEntity> patientInvoices) {
        List<PatientSessionEntity> sessions = new ArrayList<>();
        for (PatientInvoiceEntity patientInvoice : patientInvoices) {
            if (!containsSession(sessions, patientInvoice.getPatientSession().getId()))
                sessions.add(patientInvoice.getPatientSession());
        }
        List<String> sessionDiagnosis = new ArrayList<>();
        for (PatientSessionEntity patientSession : sessions) {
            patientSession.getCaseDiagnosis().stream()
                    .forEach(caseDiagnosis -> sessionDiagnosis.add(caseDiagnosis.getDiagnosisCode()));
        }
        fillDiagnosis(sessionDiagnosis);
        fillCPTDiagnosis(patientInvoices, sessionDiagnosis);
    }

    private void fillDiagnosis(List<String> sessionDiagnosis) {
        int counter = 1;
        for (String diago : sessionDiagnosis) {
            cmsForm.getField("diagnosis" + counter).setValue(diago);
            counter++;
        }
    }

    private void fillCPTDiagnosis(List<PatientInvoiceEntity> patientInvoices, List<String> sessionDiagnosis) {
        int counter = 1;
        for (PatientInvoiceEntity patientInvoice : patientInvoices) {
            List<String> indexes = new ArrayList<>();
            if (patientInvoice.getServiceLine().getDiagnoses() != null) {
                for (String serviceLineDiagnosis : patientInvoice.getServiceLine().getDiagnoses()) {
                    int index = sessionDiagnosis.indexOf(serviceLineDiagnosis);
                    if (index != -1)
                        indexes.add(getCharForNumber(index + 1));
                }
            }
            cmsForm.getField("diag" + counter).setValue(String.join("", indexes));
            counter = counter + 1;
        }
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }

    private boolean containsSession(List<PatientSessionEntity> list, Long id) {
        return list.stream().anyMatch(p -> p.getId().equals(id));
    }
}
