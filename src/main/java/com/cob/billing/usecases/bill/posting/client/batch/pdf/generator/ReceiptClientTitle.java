package com.cob.billing.usecases.bill.posting.client.batch.pdf.generator;

import com.cob.billing.model.bill.posting.paymnet.batch.pdf.ClientBatchReceiptPatientInfo;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class ReceiptClientTitle {
    public static void createTitle(Document document, ClientBatchReceiptPatientInfo clientBatchReceiptPatientInfo) throws IOException {
        Paragraph toClient = new Paragraph()
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setWidth(UnitValue.createPercentValue(50))
                .setMarginTop(5);
        toClient.add(new Text("To : ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        toClient.add(new Text(clientBatchReceiptPatientInfo.getPatientName() + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13));

        toClient.add(new Text(clientBatchReceiptPatientInfo.getAddress()
                + "," + clientBatchReceiptPatientInfo.getCity()
                + "," + clientBatchReceiptPatientInfo.getState()
                + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13));

        toClient.add(new Text(clientBatchReceiptPatientInfo.getCity()
                + "," + clientBatchReceiptPatientInfo.getState()
                + "," + clientBatchReceiptPatientInfo.getZipCode()
                + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13));
        document.add(toClient);
    }
}
