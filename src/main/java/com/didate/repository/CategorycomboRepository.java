package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.enumeration.TypeTrack;
import java.util.List;
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
        JpaRepository<CategoryCombo, String>, JpaSpecificationExecutor<CategoryCombo>, RevisionRepository<CategoryCombo, String, Integer> {
    long countByTrack(TypeTrack track);
}
