package com.cob.billing.usecases.bill.invoice.cms.filler;

import com.cob.billing.entity.bill.invoice.PatientInvoiceEntity;
import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.clinical.provider.LegacyID;
import com.cob.billing.util.DateConstructor;
import com.itextpdf.forms.PdfAcroForm;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLineCMSDocumentFiller {
    public PdfAcroForm cmsForm;

    public void create(List<SelectedSessionServiceLine> patientInvoices, PdfAcroForm cmsForm) {
        this.cmsForm = cmsForm;
        int counter = 1;
        double totalCharge = 0.0;
        double totalPayments = 0.0;
        DecimalFormat df = new DecimalFormat("#.00");
        for (SelectedSessionServiceLine sessionServiceLine : patientInvoices) {
            String[] dateOfService = DateConstructor.construct(sessionServiceLine.getSessionId().getServiceDate());
            cmsForm.getField("sv" + counter + "_mm_from").setValue(dateOfService[0]);
            cmsForm.getField("sv" + counter + "_dd_from").setValue(dateOfService[1]);
            cmsForm.getField("sv" + counter + "_yy_from").setValue(dateOfService[2]);

            cmsForm.getField("sv" + counter + "_mm_end").setValue(dateOfService[0]);
            cmsForm.getField("sv" + counter + "_dd_end").setValue(dateOfService[1]);
            cmsForm.getField("sv" + counter + "_yy_end").setValue(dateOfService[2]);
            cmsForm.getField("ch" + counter).setValue(String.valueOf(sessionServiceLine.getServiceLine().getCptCode().getCharge())
                    , df.format(sessionServiceLine.getServiceLine().getCptCode().getCharge()).replace(".", " "));
            cmsForm.getField("day" + counter).setValue(sessionServiceLine.getServiceLine().getCptCode().getUnit().toString());

            cmsForm.getField("place" + counter).setValue(sessionServiceLine.getSessionId().getPlaceOfCode().split("_")[1]);
            cmsForm.getField("cpt" + counter).setValue(sessionServiceLine.getServiceLine().getCptCode().getServiceCode());
            if (sessionServiceLine.getServiceLine().getCptCode().getModifier().length() > 0) {
                String[] modList = sessionServiceLine.getServiceLine().getCptCode().getModifier().split("\\.");
                try {
                    cmsForm.getField("mod" + counter).setValue(modList[0]);
                    cmsForm.getField("mod" + counter + "a").setValue(modList[1]);
                    cmsForm.getField("mod" + counter + "b").setValue(modList[2]);
                    cmsForm.getField("mod" + counter + "c").setValue(modList[3]);
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
            }
            cmsForm.getField("local" + counter).setValue(sessionServiceLine.getSessionId().getDoctorInfo().getDoctorNPI());
            if (sessionServiceLine.getSessionId().getDoctorInfo().getLegacyID() != null) {
                if (sessionServiceLine.getSessionId().getDoctorInfo().getLegacyID().getProviderIdQualifier() != null) {
                    cmsForm.getField("emg" + counter).setValue(sessionServiceLine.getSessionId().getDoctorInfo().getLegacyID().getProviderIdQualifier());
                    if (sessionServiceLine.getSessionId().getDoctorInfo().getLegacyID().getProviderIdQualifier().equals("ZZ"))
                        cmsForm.getField("local" + counter + "a").setValue(sessionServiceLine.getSessionId().getDoctorInfo().getTaxonomy());
                    else {
                        if (sessionServiceLine.getSessionId().getDoctorInfo().getLegacyID().getProviderId() != null)
                            cmsForm.getField("local" + counter + "a").setValue(sessionServiceLine.getSessionId().getDoctorInfo().getLegacyID().getProviderId());
                    }
                }
            }
            if (sessionServiceLine.getServiceLine().getLineNote() != null) {
                switch (counter) {
                    case 1:
                        cmsForm.getField("Suppl").setValue(sessionServiceLine.getServiceLine().getLineNote());
                        break;
                    case 2:
                        cmsForm.getField("Suppla").setValue(sessionServiceLine.getServiceLine().getLineNote());
                        break;
                    case 3:
                        cmsForm.getField("Supplb").setValue(sessionServiceLine.getServiceLine().getLineNote());
                        break;
                    case 4:
                        cmsForm.getField("Supplc").setValue(sessionServiceLine.getServiceLine().getLineNote());
                        break;
                    case 5:
                        cmsForm.getField("Suppld").setValue(sessionServiceLine.getServiceLine().getLineNote());
                        break;
                    case 6:
                        cmsForm.getField("Supple").setValue(sessionServiceLine.getServiceLine().getLineNote());
                        break;
                }
            }
            counter = counter + 1;
            totalCharge = totalCharge + sessionServiceLine.getServiceLine().getCptCode().getCharge();
            totalPayments = totalPayments + sessionServiceLine.getServiceLine().getPayments();
        }
        df.format(totalCharge);
        df.format(totalPayments);
        getSessionDiagnosis(patientInvoices);
        cmsForm.getField("t_charge").setValue(String.valueOf(totalCharge), df.format(totalCharge).replace(".", " "));
        cmsForm.getField("amt_paid").setValue(String.valueOf(totalPayments), df.format(totalPayments).replace(".", " "));
        cmsForm.getField("99icd").setFontSize(10.0f);
        cmsForm.getField("99icd").setValue("0");
        cmsForm.getField("prior_auth").setValue(patientInvoices.stream().findFirst().get().getSessionId().getAuthorizationNumber());
    }


    private void getSessionDiagnosis(List<SelectedSessionServiceLine> selectedSessionServiceLines) {
        List<PatientSession> sessions = new ArrayList<>();
        for (SelectedSessionServiceLine sessionServiceLine : selectedSessionServiceLines) {
            if (!containsSession(sessions, sessionServiceLine.getSessionId().getId()))
                sessions.add(sessionServiceLine.getSessionId());
        }
        List<String> sessionDiagnosis = new ArrayList<>();
        for (PatientSession patientSession : sessions) {
            patientSession.getCaseDiagnosis().stream()
                    .forEach(caseDiagnosis -> sessionDiagnosis.add(caseDiagnosis.getDiagnosisCode()));
        }
        fillDiagnosis(sessionDiagnosis);
        fillCPTDiagnosis(selectedSessionServiceLines, sessionDiagnosis);
    }

    private void fillDiagnosis(List<String> sessionDiagnosis) {
        int counter = 1;
        for (String diago : sessionDiagnosis) {
            cmsForm.getField("diagnosis" + counter).setValue(diago);
            counter++;
        }
    }

    private void fillCPTDiagnosis(List<SelectedSessionServiceLine> selectedSessionServiceLines, List<String> sessionDiagnosis) {
        int counter = 1;
        for (SelectedSessionServiceLine sessionServiceLine : selectedSessionServiceLines) {
            List<String> indexes = new ArrayList<>();
            if (sessionServiceLine.getServiceLine().getDiagnoses() != null) {
                for (String serviceLineDiagnosis : sessionServiceLine.getServiceLine().getDiagnoses()) {
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

    private boolean containsSession(List<PatientSession> list, Long id) {
        return list.stream().anyMatch(p -> p.getId().equals(id));
    }

}
