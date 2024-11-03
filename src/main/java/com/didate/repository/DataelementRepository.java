package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.DataElement;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dataelement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataelementRepository
    extends JpaRepository<DataElement, String>, JpaSpecificationExecutor<DataElement>, RevisionRepository<DataElement, String, Integer> {
    long countByTrack(TypeTrack track);

    @Modifying
    @Query(nativeQuery = true, value = "update dataelement set track='NONE'")
    void setTrackNone();
}
