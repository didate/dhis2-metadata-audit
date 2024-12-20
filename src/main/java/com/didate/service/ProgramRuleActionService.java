package com.didate.service;

import com.didate.domain.ProgramRuleAction;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.dto.ProgramRuleActionDTO;
import com.didate.service.dto.ProgramRuleActionFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramRuleAction}.
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

    public Page<ProgramRuleActionDTO> findAll(Pageable pageable, String id, String name);

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
    Long count();
    long countByTrack(TypeTrack track);

    void setTrackNone();

    List<ProgramRuleActionDTO> findAudits(String id);
    ProgramRuleActionFullDTO findAuditRevision(String id, Integer rev);
}
