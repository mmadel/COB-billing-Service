package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class ClientBalanceWarning {
    public static void createWarning(Document document) throws IOException {
        document.add(new Paragraph("\n"));
        Paragraph warning = new Paragraph(new Text("This form should not be saved after processing. Please cross-shred or otherwise destroy this form to protect the privacy of your patients")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setTextAlignment(TextAlignment.CENTER))
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(5);
        document.add(warning);

        SolidLine dd = new SolidLine();
        dd.setLineWidth(0.3f);
        dd.setColor(ColorConstants.BLACK);
        document.add(new LineSeparator(dd)
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(5));
    }
}
