package com.didate.service;

import com.didate.domain.Categorycombo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.didate.domain.Categorycombo}.
 */
public interface CategorycomboService {
    /**
     * Save a categorycombo.
     *
     * @param categorycombo the entity to save.
     * @return the persisted entity.
     */
    Categorycombo save(Categorycombo categorycombo);

    /**
     * Updates a categorycombo.
     *
     * @param categorycombo the entity to update.
     * @return the persisted entity.
     */
    Categorycombo update(Categorycombo categorycombo);

    /**
     * Partially updates a categorycombo.
     *
     * @param categorycombo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Categorycombo> partialUpdate(Categorycombo categorycombo);

    /**
     * Get all the categorycombos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Categorycombo> findAll(Pageable pageable);

    /**
     * Get the "id" categorycombo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Categorycombo> findOne(String id);

    /**
     * Delete the "id" categorycombo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
