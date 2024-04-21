package com.cob.billing.usecases.bill.posting.client.batch.pdf.generator;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class ReceiptClientTitle {
    public static void createTitle(Document document) throws IOException {
        Paragraph toClient = new Paragraph()
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setWidth(UnitValue.createPercentValue(50))
                .setMarginTop(5);
        toClient.add(new Text("To : ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        toClient.add(new Text("Ahmed Hany" + "\n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13));
        toClient.add(new Text("3311 Shore PKWY, APT FF, Brooklyn, NY, \n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13));
        toClient.add(new Text("Brooklyn, NY 11235 \n")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(13));
        document.add(toClient);
    }
}
