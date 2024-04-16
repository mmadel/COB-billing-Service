package com.cob.billing.usecases.bill.posting.balance.pdf;

import com.cob.billing.entity.bill.balance.PatientBalanceAccountSettings;
import com.cob.billing.model.bill.posting.balance.ClientBalanceAccount;
import com.cob.billing.model.bill.posting.balance.ClientBalanceInvoice;
import com.cob.billing.usecases.bill.posting.balance.EnrichClientBalancePaymentUSeCase;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.CustomParagraph;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.BalanceTableCreator;
import com.cob.billing.usecases.bill.posting.balance.pdf.generator.table.ColumnRuleEngine;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GenerateBalanceTablesUseCase {
    @Autowired
    private EnrichClientBalancePaymentUSeCase enrichClientBalancePaymentUSeCase;
    private ClientBalanceInvoice clientBalanceInvoice;
    private List<ClientBalanceAccount> clientBalanceAccounts;
    private Document document;
    private PatientBalanceAccountSettings patientBalanceAccountSettings;
    private boolean[] settings;

    public void createTables(ClientBalanceInvoice clientBalanceInvoice, List<ClientBalanceAccount> clientBalanceAccounts) throws IOException {
        this.clientBalanceInvoice = clientBalanceInvoice;
        this.clientBalanceAccounts = clientBalanceAccounts;
        this.settings = new boolean[]{patientBalanceAccountSettings.isRenderingProvider()
                , patientBalanceAccountSettings.isLocation(), patientBalanceAccountSettings.isPoc()};
        createFinalizeTable();
        createPendingTable();
    }

    public void setDocument(Document document) {
        if (document == null)
            throw new IllegalArgumentException("Document can't be null");
        this.document = document;
    }

    public void setBalanceAccountSettings(PatientBalanceAccountSettings patientBalanceAccountSettings) {
        if (patientBalanceAccountSettings == null)
            throw new IllegalArgumentException("PatientBalanceAccountSettings can't be null");
        this.patientBalanceAccountSettings = patientBalanceAccountSettings;
    }

    private void createFinalizeTable() throws IOException {
        BalanceTableCreator balanceTableCreator;
        String[] paragraphInputs = {"Finalized Charges - ",
                "Below are balances that are due. Each line shows a service performed. The balance is the original charge amount minus payments and adjustments applied to that service."};
        String[] standardFonts = {StandardFonts.HELVETICA_BOLD, StandardFonts.HELVETICA};
        CustomParagraph.create(paragraphInputs, standardFonts, document);


        balanceTableCreator = new BalanceTableCreator(clientBalanceInvoice.getFinalizedClientBalance());
        enrichClientBalancePaymentUSeCase.enrichWithLOC(clientBalanceAccounts, clientBalanceInvoice.getFinalizedClientBalance());
        balanceTableCreator.setTableSettings(settings);
        balanceTableCreator.create();
        document.add(balanceTableCreator.table);

    }

    private void createPendingTable() throws IOException {
        BalanceTableCreator balanceTableCreator;
        String[] pendingParagraphInputs = {"Pending Insurance - ",
                "CHARGES NOT DUE AT THIS TIME Below are services that are still pending insurance. These balances are not reflected in your total balance due, however, once your insurance has adjudicated these claims, some or all of the balance may become due"};
        String[] pendingStandardFonts = {StandardFonts.HELVETICA_BOLD, StandardFonts.HELVETICA};
        CustomParagraph.create(pendingParagraphInputs, pendingStandardFonts, document);

        balanceTableCreator = new BalanceTableCreator(clientBalanceInvoice.getPendingClientBalance());
        enrichClientBalancePaymentUSeCase.enrichWithLOC(clientBalanceAccounts, clientBalanceInvoice.getPendingClientBalance());
        balanceTableCreator.setTableSettings(settings);
        balanceTableCreator.create();
        document.add(balanceTableCreator.table);

    }
}
