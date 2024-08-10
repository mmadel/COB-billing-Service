package com.cob.billing.usecases.bill.invoice.provider;

import java.io.IOException;

public abstract class ClaimProvider {
     protected abstract void create() throws IOException, IllegalAccessException;
     public abstract void submit() throws IOException, IllegalAccessException;
}
