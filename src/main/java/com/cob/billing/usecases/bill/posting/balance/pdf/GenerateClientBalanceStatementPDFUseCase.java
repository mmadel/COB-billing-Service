package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.usecases.bill.posting.balance.pdf.generator.*;
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

        PageHeader.create(document);

        LineSeparatorCreator.create(document);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("\n"));

        PageTitle.createTitle(document);

        ClientBalanceWarning.createWarning(document);

        Table clientData = new Table(3);
        clientData.addCell(getCell(getClientName(), TextAlignment.LEFT));
        clientData.addCell(getCell(new Paragraph().setWidth(100), TextAlignment.CENTER));
        clientData.addCell(getCell(getClientMedicalRecord(), TextAlignment.RIGHT));
        document.add(clientData);

        float[] clientColumnsWidthTable = {5, 40, 30, 30, 20};
        String[] clientColumnsNameTable = {"LOC", "Service Facility", "Name", "Case", "ICD"};
        TableCreator.create(clientColumnsWidthTable, clientColumnsNameTable, document);

        String[] paragraphInputs = {"Finalized Charges -",
                "Below are balances that are due. Each line shows a service performed. The balance is the original charge amount minus payments and adjustments applied to that service."};
        String[] standardFonts = {StandardFonts.HELVETICA_BOLD, StandardFonts.HELVETICA};
        CustomParagraph.create(paragraphInputs, standardFonts, document);

        float[] balanceColumnsWidthTable = {10, 5, 5, 5, 5, 15, 5, 5, 5, 5, 5};
        String[] balanceColumnsNameTable = {"DOS", "LOC", "POS", "Service", "Units", "Provider", "Charge", "Adj", "Ins", "Patient", "Balance"};
        TableCreator.create(balanceColumnsWidthTable, balanceColumnsNameTable, document);


        String[] pendingParagraphInputs = {"Pending Insurance -",
                "CHARGES NOT DUE AT THIS TIME Below are services that are still pending insurance. These balances are not reflected in your total balance due, however, once your insurance has adjudicated these claims, some or all of the balance may become due"};
        String[] pendingStandardFonts = {StandardFonts.HELVETICA_BOLD, StandardFonts.HELVETICA};
        CustomParagraph.create(pendingParagraphInputs, pendingStandardFonts, document);


        TableCreator.create(balanceColumnsWidthTable, balanceColumnsNameTable, document);

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

}
