package com.didate.service;

import com.didate.domain.Dataelement;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Dataelement}.
 */
public interface DataelementService {
    /**
     * Save a dataelement.
     *
     * @param dataelement the entity to save.
     * @return the persisted entity.
     */
    Dataelement save(Dataelement dataelement);

    /**
     * Updates a dataelement.
     *
     * @param dataelement the entity to update.
     * @return the persisted entity.
     */
    Dataelement update(Dataelement dataelement);

    /**
     * Partially updates a dataelement.
     *
     * @param dataelement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Dataelement> partialUpdate(Dataelement dataelement);

    /**
     * Get all the dataelements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dataelement> findAll(Pageable pageable);

    /**
     * Get the "id" dataelement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Dataelement> findOne(String id);

    /**
     * Delete the "id" dataelement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
