package com.didate.repository;

import com.didate.domain.Optionset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Optionset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionsetRepository extends JpaRepository<Optionset, String> {}
