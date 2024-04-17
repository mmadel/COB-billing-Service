package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.cob.billing.entity.bill.balance.PatientBalanceAccountSettings;
import com.cob.billing.model.admin.Organization;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageBillingProviderParagraph {
    public static void createParagraph(Document document,
                                       Organization organization,
                                       PatientBalanceAccountSettings patientBalanceAccountSettings) throws IOException {
        Paragraph paragraph = new Paragraph();
        boolean[] settings = new boolean[]{patientBalanceAccountSettings.isTaxID(), patientBalanceAccountSettings.isNpi()};

        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Account Balance Statement - ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9));
        paragraph.add(new Text("This invoice covers the period up to " + getInvoiceDate()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(9));
        paragraph.add(new Text(" All fees payable to " + organization.getBusinessName()).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(9));
        paragraph.add(new Text(buildVariableParts(organization.getOrganizationData().getTaxId()
                        , organization.getNpi(), settings)).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(9))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(5);
        ;
        document.add(paragraph);
    }

    private static String buildVariableParts(String taxId, String npi, boolean[] settings) {
        StringBuilder billingProviderParagraph = new StringBuilder();
        if (settings[0])
            billingProviderParagraph.append(" Tax ID#: " + taxId + ", ");
        if (settings[1])
            billingProviderParagraph.append(" NPI: " + npi);
        return billingProviderParagraph.toString();
    }

    public static String getInvoiceDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/YYYY");
        return sdfDate.format(new Date().getTime());
    }
}
