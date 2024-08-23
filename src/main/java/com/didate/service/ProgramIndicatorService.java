package com.didate.service;

import com.didate.domain.ProgramIndicator;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramIndicator}.
 */
public interface ProgramIndicatorService {
    /**
     * Save a programIndicator.
     *
     * @param programIndicator the entity to save.
     * @return the persisted entity.
     */
    ProgramIndicator save(ProgramIndicator programIndicator);

    /**
     * Updates a programIndicator.
     *
     * @param programIndicator the entity to update.
     * @return the persisted entity.
     */
    ProgramIndicator update(ProgramIndicator programIndicator);

    /**
     * Partially updates a programIndicator.
     *
     * @param programIndicator the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramIndicator> partialUpdate(ProgramIndicator programIndicator);

    /**
     * Get all the programIndicators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramIndicator> findAll(Pageable pageable);

    /**
     * Get the "id" programIndicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramIndicator> findOne(String id);

    /**
     * Delete the "id" programIndicator.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
