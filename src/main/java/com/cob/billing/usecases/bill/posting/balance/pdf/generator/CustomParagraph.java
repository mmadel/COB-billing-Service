package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class CustomParagraph {

    public static void create(String[] paragraphInputs, String[] standardFonts, Document document) throws IOException {
        Paragraph paragraph = new Paragraph()
                .setFontSize(9)
                .setTextAlignment(TextAlignment.LEFT)
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(5);
        for (int i = 0; i < paragraphInputs.length; i++) {
            paragraph.add(new Text(paragraphInputs[i]).setFont(PdfFontFactory.createFont(standardFonts[i])).setTextAlignment(TextAlignment.LEFT));
        }
        document.add(paragraph);
    }
}
