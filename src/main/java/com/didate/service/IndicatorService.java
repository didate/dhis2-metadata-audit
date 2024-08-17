package com.didate.service;

import com.didate.domain.Indicator;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.didate.domain.Indicator}.
 */
public interface IndicatorService {
    /**
     * Save a indicator.
     *
     * @param indicator the entity to save.
     * @return the persisted entity.
     */
    Indicator save(Indicator indicator);

    /**
     * Updates a indicator.
     *
     * @param indicator the entity to update.
     * @return the persisted entity.
     */
    Indicator update(Indicator indicator);

    /**
     * Partially updates a indicator.
     *
     * @param indicator the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Indicator> partialUpdate(Indicator indicator);

    /**
     * Get all the indicators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Indicator> findAll(Pageable pageable);

    /**
     * Get the "id" indicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Indicator> findOne(String id);

    /**
     * Delete the "id" indicator.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
