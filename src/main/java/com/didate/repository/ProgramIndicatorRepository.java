package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.ProgramIndicator;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramIndicatorRepository
    extends
        JpaRepository<ProgramIndicator, String>,
        JpaSpecificationExecutor<ProgramIndicator>,
        RevisionRepository<ProgramIndicator, String, Integer> {
    long countByTrack(TypeTrack track);
}
