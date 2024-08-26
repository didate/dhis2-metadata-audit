package com.didate.service;

import com.didate.domain.CategoryCombo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CategoryCombo}.
 */
public interface CategorycomboService {
    /**
     * Save a categorycombo.
     *
     * @param categorycombo the entity to save.
     * @return the persisted entity.
     */
    CategoryCombo save(CategoryCombo categorycombo);

    /**
     * Updates a categorycombo.
     *
     * @param categorycombo the entity to update.
     * @return the persisted entity.
     */
    CategoryCombo update(CategoryCombo categorycombo);

    /**
     * Partially updates a categorycombo.
     *
     * @param categorycombo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoryCombo> partialUpdate(CategoryCombo categorycombo);

    /**
     * Get all the categorycombos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryCombo> findAll(Pageable pageable);

    /**
     * Get the "id" categorycombo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryCombo> findOne(String id);

    /**
     * Delete the "id" categorycombo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
}
