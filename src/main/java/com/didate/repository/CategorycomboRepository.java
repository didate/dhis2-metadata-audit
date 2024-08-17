package com.didate.repository;

import com.didate.domain.Categorycombo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categorycombo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorycomboRepository extends JpaRepository<Categorycombo, String> {}
