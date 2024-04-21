package com.cob.billing.service;

import com.cob.billing.model.clinical.patient.ICD10DiagnosisResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Service
public class CaseDiagnosisService {

    public ICD10DiagnosisResponse findByTerm(String term) throws IOException {
        URL url = new URL("https://clinicaltables.nlm.nih.gov/api/icd10cm/v3/search?sf=code,name&authenticity_token=&terms="
                + URLEncoder.encode(term) + "&maxList=1000");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        Gson gson = new Gson();
        Object[] code10 = gson.fromJson(response.toString(), Object[].class);
        ICD10DiagnosisResponse icd10DiagnosisResponse = new ICD10DiagnosisResponse();
        icd10DiagnosisResponse.setCountOfResult(Double.parseDouble(code10[0].toString()));
        icd10DiagnosisResponse.setListOfCode((List<String>) code10[1]);
        icd10DiagnosisResponse.setListOfCodeName((List<List<String>>) code10[3]);
        in.close();
        return icd10DiagnosisResponse;
    }
}
