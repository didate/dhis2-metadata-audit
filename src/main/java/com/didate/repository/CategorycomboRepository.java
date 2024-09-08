package com.didate.repository;

import com.didate.domain.CategoryCombo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categorycombo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorycomboRepository
    extends
        JpaRepository<CategoryCombo, String>, JpaSpecificationExecutor<CategoryCombo>, RevisionRepository<CategoryCombo, String, Integer> {}
