package com.didate.service;

import com.didate.domain.ProgramStage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramStage}.
 */
public interface ProgramStageService {
    /**
     * Save a programStage.
     *
     * @param programStage the entity to save.
     * @return the persisted entity.
     */
    ProgramStage save(ProgramStage programStage);

    /**
     * Updates a programStage.
     *
     * @param programStage the entity to update.
     * @return the persisted entity.
     */
    ProgramStage update(ProgramStage programStage);

    /**
     * Partially updates a programStage.
     *
     * @param programStage the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramStage> partialUpdate(ProgramStage programStage);

    /**
     * Get all the programStages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramStage> findAll(Pageable pageable);

    /**
     * Get all the programStages with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramStage> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" programStage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramStage> findOne(String id);

    /**
     * Delete the "id" programStage.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
}
