package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.ProgramRuleVariable;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramRuleVariable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramRuleVariableRepository
    extends
        JpaRepository<ProgramRuleVariable, String>,
        JpaSpecificationExecutor<ProgramRuleVariable>,
        RevisionRepository<ProgramRuleVariable, String, Integer> {
    long countByTrack(TypeTrack track);
}
