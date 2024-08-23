package com.didate.repository;

import com.didate.domain.ProgramRuleVariable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramRuleVariable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramRuleVariableRepository extends JpaRepository<ProgramRuleVariable, String> {}