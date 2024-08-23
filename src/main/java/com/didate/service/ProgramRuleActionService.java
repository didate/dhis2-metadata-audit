package com.didate.service;

import com.didate.domain.ProgramRuleAction;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.didate.domain.ProgramRuleAction}.
 */
public interface ProgramRuleActionService {
    /**
     * Save a programRuleAction.
     *
     * @param programRuleAction the entity to save.
     * @return the persisted entity.
     */
    ProgramRuleAction save(ProgramRuleAction programRuleAction);

    /**
     * Updates a programRuleAction.
     *
     * @param programRuleAction the entity to update.
     * @return the persisted entity.
     */
    ProgramRuleAction update(ProgramRuleAction programRuleAction);

    /**
     * Partially updates a programRuleAction.
     *
     * @param programRuleAction the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramRuleAction> partialUpdate(ProgramRuleAction programRuleAction);

    /**
     * Get all the programRuleActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramRuleAction> findAll(Pageable pageable);

    /**
     * Get the "id" programRuleAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramRuleAction> findOne(String id);

    /**
     * Delete the "id" programRuleAction.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
