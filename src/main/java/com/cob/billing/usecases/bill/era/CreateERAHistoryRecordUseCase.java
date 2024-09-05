package com.cob.billing.usecases.bill.era;

import com.cob.billing.entity.bill.era.ERAHistoryEntity;
import com.cob.billing.model.bill.era.ERAHistory;
import com.cob.billing.repositories.bill.era.ERAHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateERAHistoryRecordUseCase {
    @Autowired
    private ERAHistoryRepository eraHistoryRepository;
    @Autowired
    ModelMapper mapper;

    public void create(ERAHistory eraHistory) {
        ERAHistoryEntity eraHistoryEntity = mapper.map(eraHistory, ERAHistoryEntity.class);
        eraHistoryRepository.save(eraHistoryEntity);
    }
}
