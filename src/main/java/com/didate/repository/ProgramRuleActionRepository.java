package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.ProgramRuleAction;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramRuleAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramRuleActionRepository
    extends
        JpaRepository<ProgramRuleAction, String>,
        JpaSpecificationExecutor<ProgramRuleAction>,
        RevisionRepository<ProgramRuleAction, String, Integer> {
    long countByTrack(TypeTrack track);
}
