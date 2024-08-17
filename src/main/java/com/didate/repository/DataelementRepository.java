package com.didate.repository;

import com.didate.domain.Dataelement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dataelement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataelementRepository extends JpaRepository<Dataelement, String> {}
