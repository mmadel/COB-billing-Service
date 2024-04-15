package com.cob.billing.model.bill.posting.balance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Builder
public class ClientBalanceAccount {
    private String loc;
    private String facilityName;
    private String facilityAddress;
    private String clientName;
    private String caseTitle;
    private String icdCodes;
    private Long sessionId;
    private Long serviceLineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientBalanceAccount that = (ClientBalanceAccount) o;
        return facilityName.equals(that.facilityName) && caseTitle.equals(that.caseTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityName, caseTitle);
    }
}
