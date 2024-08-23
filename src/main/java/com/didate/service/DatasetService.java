package com.didate.service;

import com.didate.domain.Dataset;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Dataset}.
 */
public interface DatasetService {
    /**
     * Save a dataset.
     *
     * @param dataset the entity to save.
     * @return the persisted entity.
     */
    Dataset save(Dataset dataset);

    /**
     * Updates a dataset.
     *
     * @param dataset the entity to update.
     * @return the persisted entity.
     */
    Dataset update(Dataset dataset);

    /**
     * Partially updates a dataset.
     *
     * @param dataset the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Dataset> partialUpdate(Dataset dataset);

    /**
     * Get all the datasets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dataset> findAll(Pageable pageable);

    /**
     * Get all the datasets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dataset> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dataset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Dataset> findOne(String id);

    /**
     * Delete the "id" dataset.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
