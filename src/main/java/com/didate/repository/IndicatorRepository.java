package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.Indicator;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatorRepository
    extends JpaRepository<Indicator, String>, JpaSpecificationExecutor<Indicator>, RevisionRepository<Indicator, String, Integer> {
    long countByTrack(TypeTrack track);

    @Modifying
    @Query(nativeQuery = true, value = "update indicator set track='NONE'")
    void setTrackNone();
}
