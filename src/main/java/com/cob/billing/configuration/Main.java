package com.cob.billing.configuration;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException {
        try {
            // Step 1: Remove restrictions on the existing PDF
            PdfReader reader = new PdfReader("C:\\cob\\documents\\billing\\form-cms1500.pdf");
            PdfCopyFields copy = new PdfCopyFields(new FileOutputStream("unlocked-form.pdf"));
            copy.addDocument(reader);
            copy.close();
            reader.close();

            // Step 2: Extract form data from the unlocked PDF
            reader = new PdfReader("unlocked-form.pdf");
            AcroFields fields = reader.getAcroFields();

            // Get field names and values

            reader.close();

            // Step 3: Fill in the form with new values
            PdfReader unlockedReader = new PdfReader("unlocked-form.pdf");
            PdfStamper stamper = new PdfStamper(unlockedReader, new FileOutputStream("filled-form.pdf"));
            AcroFields unlockedFields = stamper.getAcroFields();
//            unlockedFields.setField("insurance_name", "New York Network Management");
//            unlockedFields.setField("insurance_address", "PO BOX 8888");
//            unlockedFields.setField("insurance_city_state_zip", "NEW YORK, NY 10116");
//            unlockedFields.setField("insurance_type", "Group");
//            unlockedFields.setField("insurance_id", "99999999");

            Map<String, AcroFields.Item> fieldMap = unlockedFields.getFields();
            for (String fieldName : fieldMap.keySet()) {
                // Get field value
                String value = unlockedFields.getField(fieldName);
                System.out.println("Field Name: " + fieldName + ", Value: " + value);
            }
            stamper.close();

            System.out.println("Filled PDF generated successfully.");

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

}
