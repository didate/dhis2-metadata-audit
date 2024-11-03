package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrackedEntityAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackedEntityAttributeRepository
    extends
        JpaRepository<TrackedEntityAttribute, String>,
        JpaSpecificationExecutor<TrackedEntityAttribute>,
        RevisionRepository<TrackedEntityAttribute, String, Integer> {
    long countByTrack(TypeTrack track);

    @Modifying
    @Query(nativeQuery = true, value = "update tracked_entity_attribute set track='NONE'")
    void setTrackNone();
}
