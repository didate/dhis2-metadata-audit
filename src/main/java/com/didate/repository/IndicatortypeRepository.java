package com.didate.repository;

import com.didate.domain.Indicatortype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicatortype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatortypeRepository extends JpaRepository<Indicatortype, String> {}
