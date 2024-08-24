package com.didate.service;

import com.didate.domain.IndicatorType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link IndicatorType}.
 */
public interface IndicatortypeService {
    /**
     * Save a indicatortype.
     *
     * @param indicatortype the entity to save.
     * @return the persisted entity.
     */
    IndicatorType save(IndicatorType indicatortype);

    /**
     * Updates a indicatortype.
     *
     * @param indicatortype the entity to update.
     * @return the persisted entity.
     */
    IndicatorType update(IndicatorType indicatortype);

    /**
     * Partially updates a indicatortype.
     *
     * @param indicatortype the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndicatorType> partialUpdate(IndicatorType indicatortype);

    /**
     * Get all the indicatortypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IndicatorType> findAll(Pageable pageable);

    /**
     * Get the "id" indicatortype.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndicatorType> findOne(String id);

    /**
     * Delete the "id" indicatortype.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
