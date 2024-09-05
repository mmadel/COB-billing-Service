package com.cob.billing.repositories.bill.era;

import com.cob.billing.entity.bill.era.ERAHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ERAHistoryRepository extends CrudRepository<ERAHistoryEntity, Long> {
    @Query("SELECT erah from ERAHistoryEntity erah  where erah.eraId IN :eraIds")
    List<ERAHistoryEntity> findErasByERAIds(@Param("eraIds") List<Integer> eraIds);

    Optional<ERAHistoryEntity> findByEraId(Integer eraId);
}
