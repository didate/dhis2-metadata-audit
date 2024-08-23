package com.didate.repository;

import com.didate.domain.ProgramIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramIndicatorRepository extends JpaRepository<ProgramIndicator, String> {}
