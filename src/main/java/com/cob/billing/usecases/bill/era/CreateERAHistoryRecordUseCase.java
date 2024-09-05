package com.cob.billing.usecases.bill.era;

import com.cob.billing.entity.bill.era.ERAHistoryEntity;
import com.cob.billing.model.bill.posting.era.ERAHistory;
import com.cob.billing.repositories.bill.era.ERAHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateERAHistoryRecordUseCase {
    @Autowired
    private ERAHistoryRepository eraHistoryRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    ERAHistoryMapper eraHistoryMapper;

    public void create(ERAHistory eraHistory) {
        Optional<ERAHistoryEntity> entity = eraHistoryRepository.findByEraId(eraHistory.getEra().getEraId());
        ERAHistoryEntity toBeSaved = null;
        if(entity.isEmpty()){
            toBeSaved = mapper.map(eraHistory, ERAHistoryEntity.class);
            toBeSaved.setArchive(false);
            toBeSaved.setEraId(eraHistory.getEra().getEraId());
        }else{
            toBeSaved = entity.get();
            toBeSaved.getEraLines().addAll(eraHistory.getEraLines());
        }
        eraHistoryRepository.save(toBeSaved);
    }
}
