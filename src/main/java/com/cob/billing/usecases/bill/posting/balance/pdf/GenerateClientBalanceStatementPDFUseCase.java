package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.usecases.bill.posting.balance.pdf.generator.PageHeader;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.LineSeparatorCreator;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.PageTitle;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class GenerateClientBalanceStatementPDFUseCase {

    public byte[] generate() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;
        Document document = new Document(pdfDoc);


        PdfFont normalText = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        PageHeader.create(document);

        LineSeparatorCreator.create(document);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("\n"));

        PageTitle.createTitle(document);

        document.add(new Paragraph("\n"));
        Paragraph warning = new Paragraph(new Text("This form should not be saved after processing. Please cross-shred or otherwise destroy this form to protect the privacy of your patients").setFont(normalText).setTextAlignment(TextAlignment.CENTER))
                .setFontSize(7)
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

        Table clientData = new Table(3);
        clientData.addCell(getCell(getClientName(), TextAlignment.LEFT));
        clientData.addCell(getCell(new Paragraph().setWidth(100), TextAlignment.CENTER));
        clientData.addCell(getCell(getClientMedicalRecord(), TextAlignment.RIGHT));
        document.add(clientData);

        Table clientTable = new Table(UnitValue.createPercentArray(5))
                .useAllAvailableWidth()
                .setMarginTop(10);
        Cell LOCHeader = new Cell().add(new Paragraph("LOC")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;
        Cell serviceFacilityHeader = new Cell().add(new Paragraph("Service Facility")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)

                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;

        Cell nameHeader = new Cell().add(new Paragraph("Name")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;

        Cell caseHeader = new Cell().add(new Paragraph("Case")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell ICDHeader = new Cell().add(new Paragraph("ICD")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;
        clientTable.addCell(LOCHeader);
        clientTable.addCell(serviceFacilityHeader);
        clientTable.addCell(nameHeader);
        clientTable.addCell(caseHeader);
        clientTable.addCell(ICDHeader);
        document.add(clientTable);

        Paragraph finalizeDailog = new Paragraph()
                .setFontSize(7)
                .setTextAlignment(TextAlignment.LEFT)
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(5);
        finalizeDailog.add(new Text("Finalized Charges -").setFont(boldFont).setTextAlignment(TextAlignment.LEFT));
        finalizeDailog.add(new Text("Below are balances that are due. Each line shows a service performed. The balance is the original charge amount minus payments\n" +
                "and adjustments applied to that service.").setFont(normalText).setTextAlignment(TextAlignment.LEFT));
        document.add(finalizeDailog);
        float[] columnWidths = {10, 5, 5, 5, 15, 5, 5, 5, 5, 5, 5};
        Table finalizeTable = new Table(UnitValue.createPercentArray(columnWidths));
        finalizeTable.setWidth(UnitValue.createPercentValue(100));
        Cell DOsHeader = new Cell().add(new Paragraph("DOS")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setStrokeWidth(30)
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;
        Cell LOCCHeader = new Cell().add(new Paragraph("LOC")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;

        Cell POSHeader = new Cell().add(new Paragraph("POS")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;

        Cell ServiceHeader = new Cell().add(new Paragraph("Service")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell ProviderHeader = new Cell().add(new Paragraph("Provider")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell ChargeHeader = new Cell().add(new Paragraph("Charge")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell AdjHeader = new Cell().add(new Paragraph("Adj")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell InsHeader = new Cell().add(new Paragraph("Ins")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell PatientHeader = new Cell().add(new Paragraph("Patient")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell balanceHeader = new Cell().add(new Paragraph("Balance")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        finalizeTable.addHeaderCell(DOsHeader);
        finalizeTable.addHeaderCell(LOCCHeader);
        finalizeTable.addHeaderCell(POSHeader);
        finalizeTable.addHeaderCell(ServiceHeader);
        finalizeTable.addHeaderCell(ProviderHeader);
        finalizeTable.addHeaderCell(ChargeHeader);
        finalizeTable.addHeaderCell(AdjHeader);
        finalizeTable.addHeaderCell(InsHeader);
        finalizeTable.addHeaderCell(PatientHeader);
        finalizeTable.addHeaderCell(balanceHeader);
        document.add(finalizeTable);
        Paragraph pendingDailog = new Paragraph()
                .setFontSize(7)
                .setTextAlignment(TextAlignment.LEFT)
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(5);
        pendingDailog.add(new Text("Pending Insurance -").setFont(boldFont).setTextAlignment(TextAlignment.LEFT));
        pendingDailog.add(new Text("CHARGES NOT DUE AT THIS TIME Below are services that are still pending insurance. These balances are not reflected in your\n" +
                "total balance due, however, once your insurance has adjudicated these claims, some or all of the balance may become due.").setFont(normalText).setTextAlignment(TextAlignment.LEFT));
        document.add(pendingDailog);

        Table pendingTable = new Table(UnitValue.createPercentArray(columnWidths));
        pendingTable.setWidth(UnitValue.createPercentValue(100));
        Cell DOsHeader1 = new Cell().add(new Paragraph("DOS")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setStrokeWidth(30)
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;
        Cell LOCCHeader1 = new Cell().add(new Paragraph("LOC")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;

        Cell POSHeader1 = new Cell().add(new Paragraph("POS")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);
        ;

        Cell ServiceHeader1 = new Cell().add(new Paragraph("Service")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell ProviderHeader1 = new Cell().add(new Paragraph("Provider")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell ChargeHeader1 = new Cell().add(new Paragraph("Charge")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell AdjHeader1 = new Cell().add(new Paragraph("Adj")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell InsHeader1 = new Cell().add(new Paragraph("Ins")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell PatientHeader1 = new Cell().add(new Paragraph("Patient")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        Cell balanceHeader1 = new Cell().add(new Paragraph("Balance")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(9)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(new DeviceRgb(0, 0, 153))
                .setPadding(0);

        pendingTable.addHeaderCell(DOsHeader1);
        pendingTable.addHeaderCell(LOCCHeader1);
        pendingTable.addHeaderCell(POSHeader1);
        pendingTable.addHeaderCell(ServiceHeader1);
        pendingTable.addHeaderCell(ProviderHeader1);
        pendingTable.addHeaderCell(ChargeHeader1);
        pendingTable.addHeaderCell(AdjHeader1);
        pendingTable.addHeaderCell(InsHeader1);
        pendingTable.addHeaderCell(PatientHeader1);
        pendingTable.addHeaderCell(balanceHeader1);
        document.add(pendingTable);

        document.close();
        return outputStream.toByteArray();
    }

    private Paragraph getClientName() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Client Name: ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(8));
        paragraph.add(new Text("weal ahmed").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(8));
        return paragraph;
    }

    private Paragraph getClientMedicalRecord() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Medical Record Number: ").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(8));
        paragraph.add(new Text("35933TB21380").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(8));
        return paragraph;
    }

    private Cell getCell(IBlockElement paragraph, TextAlignment alignment) {
        Cell cell = new Cell().add(paragraph);
        //cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Paragraph getInvoiceDateParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Invoice date: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("03/27/2024").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    private Paragraph getInvoiceNumberParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("Invoice Number: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("S000621").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }

    private Paragraph getBalanceParagraph() throws IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.setTextAlignment(TextAlignment.LEFT);
        paragraph.add(new Text("balance due: \n").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(6));
        paragraph.add(new Text("$20.00").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(10));
        return paragraph;
    }
}
