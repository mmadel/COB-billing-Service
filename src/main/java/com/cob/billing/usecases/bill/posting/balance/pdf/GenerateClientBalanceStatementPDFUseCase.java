package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import com.cob.billing.model.bill.posting.balance.PatientBalanceSettings;
import com.cob.billing.usecases.bill.posting.balance.CollectClientBalanceAccountUseCase;
import com.cob.billing.usecases.bill.posting.balance.RetrieveClientBalanceSettingsUseCase;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.ClientBalanceWarning;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.LineSeparatorCreator;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.PageHeader;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.PageTitle;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.LocationTableCreator;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class GenerateClientBalanceStatementPDFUseCase {

    @Autowired
    CollectClientBalanceAccountUseCase collectClientBalanceAccountUseCase;
    @Autowired
    GenerateBalanceTablesUseCase generateBalanceTablesUseCase;
    @Autowired
    RetrieveClientBalanceSettingsUseCase retrieveClientBalanceSettingsUseCase;
    PatientBalanceSettings patientBalanceSettings;

    public byte[] generate(ClientBalanceInvoice clientBalanceInvoice) throws IOException {
        patientBalanceSettings = retrieveClientBalanceSettingsUseCase.retrieve();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;
        Document document = new Document(pdfDoc, pageSize);
        List<ClientBalanceAccount> clientBalanceAccounts = collectClientBalanceAccountUseCase.collect(clientBalanceInvoice);

        double totalBalance = clientBalanceInvoice.getFinalizedClientBalance().stream()
                .mapToDouble(ClientBalancePayment::getBalance)
                .sum();
        PageHeader.create(document, totalBalance,patientBalanceSettings.getPatientBalanceBillingProviderSettings());

        LineSeparatorCreator.create(document);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("\n"));

        PageTitle.createTitle(document, clientBalanceAccounts.stream().findFirst().get(), patientBalanceSettings.getPatientBalanceBillingProviderSettings());

        ClientBalanceWarning.createWarning(document);

        document.add(new Paragraph("\n"));

        LocationTableCreator locationTableCreator = new LocationTableCreator(clientBalanceAccounts);
        boolean[] settings = new boolean[]{patientBalanceSettings.getPatientBalanceAccountSettings().isIcdCodes()};
        locationTableCreator.setTableSettings(settings);
        locationTableCreator.create();
        document.add(locationTableCreator.table);

        generateBalanceTablesUseCase.setDocument(document);
        generateBalanceTablesUseCase.setBalanceAccountSettings(patientBalanceSettings.getPatientBalanceAccountSettings());
        generateBalanceTablesUseCase.createTables(clientBalanceInvoice, clientBalanceAccounts);
        document.close();
        return outputStream.toByteArray();
    }
}
