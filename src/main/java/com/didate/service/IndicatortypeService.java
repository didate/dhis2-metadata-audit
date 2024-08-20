package com.didate.service;

import com.didate.domain.Indicatortype;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.didate.domain.Indicatortype}.
 */
public interface IndicatortypeService {
    /**
     * Save a indicatortype.
     *
     * @param indicatortype the entity to save.
     * @return the persisted entity.
     */
    Indicatortype save(Indicatortype indicatortype);

    /**
     * Updates a indicatortype.
     *
     * @param indicatortype the entity to update.
     * @return the persisted entity.
     */
    Indicatortype update(Indicatortype indicatortype);

    /**
     * Partially updates a indicatortype.
     *
     * @param indicatortype the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Indicatortype> partialUpdate(Indicatortype indicatortype);

    /**
     * Get all the indicatortypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Indicatortype> findAll(Pageable pageable);

    /**
     * Get the "id" indicatortype.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Indicatortype> findOne(String id);

    /**
     * Delete the "id" indicatortype.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
