package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.IndicatorType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicatortype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatortypeRepository extends JpaRepository<IndicatorType, String>, RevisionRepository<IndicatorType, String, Integer> {}
