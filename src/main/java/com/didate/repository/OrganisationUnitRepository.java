package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.OrganisationUnit;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganisationUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationUnitRepository
    extends
        JpaRepository<OrganisationUnit, String>,
        JpaSpecificationExecutor<OrganisationUnit>,
        RevisionRepository<OrganisationUnit, String, Integer> {
    long countByTrack(TypeTrack track);

    @Modifying
    @Query(nativeQuery = true, value = "update organisation_unit set track='NONE'")
    void setTrackNone();
}
