package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.OptionGroup;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OptionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionGroupRepository
    extends JpaRepository<OptionGroup, String>, JpaSpecificationExecutor<OptionGroup>, RevisionRepository<OptionGroup, String, Integer> {
    long countByTrack(TypeTrack track);
}
