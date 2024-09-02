package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.Indicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, String>, RevisionRepository<Indicator, String, Integer> {}
