package com.didate.repository;

import com.didate.domain.ProgramRuleAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramRuleAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramRuleActionRepository extends JpaRepository<ProgramRuleAction, String> {}
