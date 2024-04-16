package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.cob.billing.entity.bill.balance.PatientBalanceBillingProviderSettings;
import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class PageTitle {
    public static Paragraph createTitle(Document document, ClientBalanceAccount clientBalanceAccount, PatientBalanceBillingProviderSettings patientBalanceBillingProviderSettings) throws IOException {
        Paragraph toClient = new Paragraph()
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setWidth(UnitValue.createPercentValue(50))
                .setMarginTop(5);
        toClient.add(new Text("To : ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
        toClient.add(new Text(clientBalanceAccount.getClientName() + "\n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));
        toClient.add(new Text(clientBalanceAccount.getClientAddress()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)));
        document.add(toClient);
        document.add(new Paragraph("\n"));

        Paragraph toProvider = new Paragraph()
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setWidth(UnitValue.createPercentValue(90))
                .setMarginTop(5);
        toProvider.add(new Text("Please Remit To : ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setTextAlignment(TextAlignment.LEFT));
        toProvider.add(new Text(patientBalanceBillingProviderSettings.getLineOne() + " \n " +
                patientBalanceBillingProviderSettings.getLineTwo() + " \n " +
                patientBalanceBillingProviderSettings.getLineThree()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setTextAlignment(TextAlignment.LEFT));
        document.add(toProvider);
        return toProvider;
    }
}
