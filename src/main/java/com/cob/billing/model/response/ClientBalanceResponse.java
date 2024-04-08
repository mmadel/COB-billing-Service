package com.cob.billing.model.response;

import com.cob.billing.model.bill.posting.balance.ClientBalance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ClientBalanceResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<ClientBalance> records;
}
