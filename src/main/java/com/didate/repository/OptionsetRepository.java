package com.didate.repository;

import com.didate.domain.OptionSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OptionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionsetRepository extends JpaRepository<OptionSet, String> {}
