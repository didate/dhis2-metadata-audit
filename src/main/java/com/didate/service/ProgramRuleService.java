package com.didate.service;

import com.didate.domain.ProgramRule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramRule}.
 */
public interface ProgramRuleService {
    /**
     * Save a programRule.
     *
     * @param programRule the entity to save.
     * @return the persisted entity.
     */
    ProgramRule save(ProgramRule programRule);

    /**
     * Updates a programRule.
     *
     * @param programRule the entity to update.
     * @return the persisted entity.
     */
    ProgramRule update(ProgramRule programRule);

    /**
     * Partially updates a programRule.
     *
     * @param programRule the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramRule> partialUpdate(ProgramRule programRule);

    /**
     * Get all the programRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramRule> findAll(Pageable pageable);

    /**
     * Get the "id" programRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramRule> findOne(String id);

    /**
     * Delete the "id" programRule.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
}
