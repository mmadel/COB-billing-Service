package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.usecases.bill.posting.balance.CollectClientBalanceAccount;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.*;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.BalanceTableCreator;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ClientTableCreator;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class GenerateClientBalanceStatementPDFUseCase {

    @Autowired
    CollectClientBalanceAccount clientBalanceAccount;
    public byte[] generate(ClientBalanceInvoice clientBalanceInvoice) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;
        Document document = new Document(pdfDoc,pageSize);
        PageHeader.create(document);

        LineSeparatorCreator.create(document);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("\n"));

        PageTitle.createTitle(document);

        ClientBalanceWarning.createWarning(document);

        document.add(new Paragraph("\n"));

        ClientTableCreator clientTableCreator = new ClientTableCreator();
        clientTableCreator.build(clientBalanceAccount.collect(clientBalanceInvoice));
        document.add(clientTableCreator.table);
        String[] paragraphInputs = {"Finalized Charges - ",
                "Below are balances that are due. Each line shows a service performed. The balance is the original charge amount minus payments and adjustments applied to that service."};
        String[] standardFonts = {StandardFonts.HELVETICA_BOLD, StandardFonts.HELVETICA};
        CustomParagraph.create(paragraphInputs, standardFonts, document);


        BalanceTableCreator balanceTableCreator = new BalanceTableCreator();
        balanceTableCreator.build(clientBalanceInvoice.getFinalizedClientBalance());
        document.add(balanceTableCreator.table);


        String[] pendingParagraphInputs = {"Pending Insurance - ",
                "CHARGES NOT DUE AT THIS TIME Below are services that are still pending insurance. These balances are not reflected in your total balance due, however, once your insurance has adjudicated these claims, some or all of the balance may become due"};
        String[] pendingStandardFonts = {StandardFonts.HELVETICA_BOLD, StandardFonts.HELVETICA};
        CustomParagraph.create(pendingParagraphInputs, pendingStandardFonts, document);

        balanceTableCreator.build(clientBalanceInvoice.getPendingClientBalance());
        document.add(balanceTableCreator.table);
        document.close();
        return outputStream.toByteArray();
    }



}
