package com.didate.service;

import com.didate.domain.ProgramRuleVariable;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramRuleVariable}.
 */
public interface ProgramRuleVariableService {
    /**
     * Save a programRuleVariable.
     *
     * @param programRuleVariable the entity to save.
     * @return the persisted entity.
     */
    ProgramRuleVariable save(ProgramRuleVariable programRuleVariable);

    /**
     * Updates a programRuleVariable.
     *
     * @param programRuleVariable the entity to update.
     * @return the persisted entity.
     */
    ProgramRuleVariable update(ProgramRuleVariable programRuleVariable);

    /**
     * Partially updates a programRuleVariable.
     *
     * @param programRuleVariable the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramRuleVariable> partialUpdate(ProgramRuleVariable programRuleVariable);

    /**
     * Get all the programRuleVariables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramRuleVariable> findAll(Pageable pageable);

    /**
     * Get the "id" programRuleVariable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramRuleVariable> findOne(String id);

    /**
     * Delete the "id" programRuleVariable.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
}
