package com.cob.billing.usecases.bill.invoice.electronic.filler;

import com.cob.billing.model.bill.invoice.SelectedSessionServiceLine;
import com.cob.billing.model.clinical.patient.session.PatientSession;
import com.cob.billing.model.integration.claimmd.Charge;
import com.cob.billing.model.integration.claimmd.Claim;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Component
public class ServiceLinesFiller {
    public void fill(List<SelectedSessionServiceLine> patientInvoiceRecords, Claim claim) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Charge> charges = new ArrayList<>();
        Double totalCharge = 0.0;
        for (SelectedSessionServiceLine sessionServiceLine : patientInvoiceRecords) {
            Charge charge = new Charge();
            charge.setCharge(String.valueOf(sessionServiceLine.getServiceLine().getCptCode().getCharge()));
            charge.setCharge_record_type("UN");
            SimpleDateFormat serviceDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            charge.setFrom_date(serviceDateFormat.format(sessionServiceLine.getSessionId().getServiceDate()));
            charge.setThru_date(serviceDateFormat.format(sessionServiceLine.getSessionId().getServiceDate()));
            charge.setPlace_of_service(sessionServiceLine.getSessionId().getPlaceOfCode().split("_")[1]);
            charge.setProc_code(sessionServiceLine.getServiceLine().getCptCode().getServiceCode());
             /*TODO
                generate id with max 40 length
             */
            //charge.setRemote_chgid();
            if (sessionServiceLine.getServiceLine().getCptCode().getModifier().length() > 0) {
                String[] modList = sessionServiceLine.getServiceLine().getCptCode().getModifier().split("\\.");
                for (int i = 0; i < modList.length; i++) {
                    if (modList[i] != null) {
                        Method method = Charge.class.getMethod("setMod" + (i + 1), String.class);
                        method.invoke(charge, modList[i]);
                    }
                }
            }
            List<PatientSession> sessions = new ArrayList<>();
            if (!containsSession(sessions, sessionServiceLine.getSessionId().getId()))
                sessions.add(sessionServiceLine.getSessionId());
            List<String> sessionDiagnosis = new ArrayList<>();
            for (PatientSession patientSession : sessions) {
                patientSession.getCaseDiagnosis().stream()
                        .forEach(caseDiagnosis -> sessionDiagnosis.add(caseDiagnosis.getDiagnosisCode()));
            }
            List<String> indexes = new ArrayList<>();
            if (sessionServiceLine.getServiceLine().getDiagnoses() != null) {
                for (String serviceLineDiagnosis : sessionServiceLine.getServiceLine().getDiagnoses()) {
                    int index = sessionDiagnosis.indexOf(serviceLineDiagnosis);
                    if (index != -1)
                        indexes.add(getCharForNumber(index + 1));
                }
            }
            charge.setDiag_ref(String.join("", indexes));
            charge.setUnits(sessionServiceLine.getServiceLine().getCptCode().getUnit().toString());
            charges.add(charge);
            totalCharge = totalCharge + sessionServiceLine.getServiceLine().getCptCode().getCharge();
        }
        getSessionDiagnosis(patientInvoiceRecords, claim);
        claim.setCharge(charges);
        claim.setTotal_charge(totalCharge.toString());
    }
    private void getSessionDiagnosis(List<SelectedSessionServiceLine> selectedSessionServiceLines, Claim claim) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
        fillDiagnosis(sessionDiagnosis, claim);
    }
    private void fillDiagnosis(List<String> sessionDiagnosis, Claim claim) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int counter = 1;
        for (String diago : sessionDiagnosis) {
            Method method = Claim.class.getMethod("setDiag_" + counter, String.class);
            method.invoke(claim, diago);
            counter++;
        }
    }
    private boolean containsSession(List<PatientSession> list, Long id) {
        return list.stream().anyMatch(p -> p.getId().equals(id));
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }
}
