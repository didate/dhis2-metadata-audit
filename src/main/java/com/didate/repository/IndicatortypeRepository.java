package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.IndicatorType;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicatortype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatortypeRepository
    extends
        JpaRepository<IndicatorType, String>, JpaSpecificationExecutor<IndicatorType>, RevisionRepository<IndicatorType, String, Integer> {
    long countByTrack(TypeTrack track);

    @Modifying
    @Query(nativeQuery = true, value = "update indicatortype set track='NONE'")
    void setTrackNone();
}
