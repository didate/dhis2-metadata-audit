package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.OptionGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OptionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, String>, RevisionRepository<OptionGroup, String, Integer> {}
