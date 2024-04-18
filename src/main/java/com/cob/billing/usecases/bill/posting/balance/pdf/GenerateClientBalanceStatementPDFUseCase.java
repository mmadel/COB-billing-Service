package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.model.admin.Organization;
import com.cob.billing.model.bill.posting.balance.*;
import com.cob.billing.usecases.admin.organization.RetrievingOrganizationUseCase;
import com.cob.billing.usecases.bill.posting.balance.CollectClientBalanceAccountUseCase;
import com.cob.billing.usecases.bill.posting.balance.RetrieveClientBalanceSettingsUseCase;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.*;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.LocationTableCreator;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ProviderTableCreator;
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
    CreateBalanceTablesUseCase createBalanceTablesUseCase;
    @Autowired
    RetrieveClientBalanceSettingsUseCase retrieveClientBalanceSettingsUseCase;
    @Autowired
    CreateProviderTableUseCase createProviderTableUseCase;
    @Autowired
    RetrievingOrganizationUseCase retrievingOrganizationUseCase;

    @Autowired
    CreateBalanceTableUseCase createBalanceTableUseCase;

    PatientBalanceSettings patientBalanceSettings;

    public byte[] generate(ClientBalanceInvoice clientBalanceInvoice) throws IOException {
        patientBalanceSettings = retrieveClientBalanceSettingsUseCase.retrieve();
        Organization billingProvider = retrievingOrganizationUseCase.findDefault();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);

        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = PageSize.A4;
        Document document = new Document(pdfDoc, pageSize);
        List<ClientBalanceAccount> clientBalanceAccounts = collectClientBalanceAccountUseCase.collect(clientBalanceInvoice);

        double totalBalance = clientBalanceInvoice.getFinalizedClientBalance().stream()
                .mapToDouble(ClientBalancePayment::getBalance)
                .sum();
        //Create Page Header
        PageHeader.createHeader(document, totalBalance, patientBalanceSettings.getPatientBalanceBillingProviderSettings());

        LineSeparatorCreator.create(document);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("\n"));

        //Create Page Title
        PageTitle.createTitle(document,
                clientBalanceAccounts.stream().findFirst().get(),
                patientBalanceSettings.getPatientBalanceBillingProviderSettings(),
                patientBalanceSettings.getPatientBalanceAccountSettings().isPatientDOB());

        //Create Warning
        ClientBalanceWarning.createWarning(document);

        //Create Billing Provider Paragraph
        PageBillingProviderParagraph.createParagraph(document,
                billingProvider,
                patientBalanceSettings.getPatientBalanceAccountSettings());



        List<ClientBalanceLocation> clientBalanceLocations = createBalanceTableUseCase.createTable(document,clientBalanceInvoice,patientBalanceSettings);

        //Create Balance Tables <Finalized And Pending>
        createBalanceTablesUseCase.setDocument(document);
        createBalanceTablesUseCase.setBalanceAccountSettings(patientBalanceSettings.getPatientBalanceAccountSettings());
        createBalanceTablesUseCase.createTables(clientBalanceInvoice, clientBalanceLocations);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        //Create Provider Table
        if (patientBalanceSettings.getPatientBalanceAccountSettings().isRenderingProvider()) {
            createProviderTableUseCase.setDocument(document);
            createProviderTableUseCase.createTable(clientBalanceInvoice);
        }

        document.close();
        return outputStream.toByteArray();
    }
}
