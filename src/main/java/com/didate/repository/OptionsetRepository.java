package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.OptionSet;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OptionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionsetRepository
    extends JpaRepository<OptionSet, String>, JpaSpecificationExecutor<OptionSet>, RevisionRepository<OptionSet, String, Integer> {
    long countByTrack(TypeTrack track);
}
