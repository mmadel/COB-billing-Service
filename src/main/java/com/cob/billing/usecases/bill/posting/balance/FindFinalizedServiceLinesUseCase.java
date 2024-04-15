package com.cob.billing.usecases.bill.posting.balance;

import com.cob.billing.entity.clinical.patient.session.PatientSessionEntity;
import com.cob.billing.model.bill.posting.balance.ClientBalancePayment;
import com.cob.billing.model.clinical.patient.session.filter.PatientSessionSearchCriteria;
import com.cob.billing.model.response.ClientBalanceResponse;
import com.cob.billing.repositories.clinical.session.PatientSessionRepository;
import com.cob.billing.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindFinalizedServiceLinesUseCase {
    @Autowired
    PatientSessionRepository patientSessionRepository;
    @Autowired
    ConstructClientBalanceStatementsUseCase constructClientBalanceStatementsUseCase;

    public ClientBalanceResponse find(int offset, int limit, Long patientId, PatientSessionSearchCriteria patientSessionSearchCriteria) {
        List<PatientSessionEntity> patientSessionEntities = patientSessionRepository.findClientFinalizedSessions(patientId
                , patientSessionSearchCriteria.getStartDate()
                , patientSessionSearchCriteria.getEndDate());
        List<ClientBalancePayment> clientBalanceStatements = constructClientBalanceStatementsUseCase.constructStatement(patientSessionEntities);

        List<ClientBalancePayment> records = PaginationUtil.paginate(clientBalanceStatements, offset, limit);
        return ClientBalanceResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records(records.size())
                .records(records) //records
                .build();
    }
}
