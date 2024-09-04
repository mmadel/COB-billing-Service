package com.cob.billing.usecases.bill.posting.era;

import com.cob.billing.model.bill.posting.era.ERADataDetailTransferModel;
import com.cob.billing.model.bill.posting.era.ERADataTransferModel;
import com.cob.billing.model.response.ERAResponse;
import com.cob.billing.usecases.integration.claim.md.RetrieveERAListUseCase;
import com.cob.billing.util.ERAListSorterByDate;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FetchERAListUseCase {
    @Autowired
    RetrieveERAListUseCase retrieveERAListUseCase;
    @Autowired
    FetchERADetailsUseCase fetchERADetailsUseCase;

    public ERAResponse fetch(Pageable paging) {
        List<ERADataTransferModel> eraDataTransferModels = retrieveERAListUseCase.getList(0L).getEra()
                .stream().map(eraModel -> {

                    return ERADataTransferModel.builder()
                            .eraId(eraModel.getEraid())
                            .payerName(eraModel.getPayer_name())
                            .receivedDate(eraModel.getReceived_time())
                            .checkNumber(eraModel.getCheck_number())
                            .paidAmount(new BigDecimal(eraModel.getPaid_amount()))
                            .seen(false)
                            .build();
                })
                .collect(Collectors.toList());
        new ERAListSorterByDate().sortByReceivedDateDesc(eraDataTransferModels);

        List<ERADataTransferModel> records = PaginationUtil.paginate(eraDataTransferModels, paging.getPageNumber() + 1, paging.getPageSize());
        records.stream().forEach(eraDataTransferModel -> {
            ERADataDetailTransferModel eraDataDetail = fetchERADetailsUseCase.fetch(eraDataTransferModel.getEraId());
            eraDataTransferModel.setEraDetails(eraDataDetail);
            eraDataTransferModel.setLines(eraDataDetail.getLines().size());
        });
        return ERAResponse.builder()
                .number_of_records(eraDataTransferModels.size())
                .number_of_matching_records((int) records.size())
                .records(records)
                .build();

    }
}
