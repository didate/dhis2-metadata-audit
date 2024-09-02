package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.ProgramRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramRuleRepository extends JpaRepository<ProgramRule, String>, RevisionRepository<ProgramRule, String, Integer> {}
