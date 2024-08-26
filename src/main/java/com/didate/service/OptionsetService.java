package com.didate.service;

import com.didate.domain.OptionSet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OptionSet}.
 */
public interface OptionsetService {
    /**
     * Save a optionset.
     *
     * @param optionset the entity to save.
     * @return the persisted entity.
     */
    OptionSet save(OptionSet optionset);

    /**
     * Updates a optionset.
     *
     * @param optionset the entity to update.
     * @return the persisted entity.
     */
    OptionSet update(OptionSet optionset);

    /**
     * Partially updates a optionset.
     *
     * @param optionset the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OptionSet> partialUpdate(OptionSet optionset);

    /**
     * Get all the optionsets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionSet> findAll(Pageable pageable);

    /**
     * Get the "id" optionset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionSet> findOne(String id);

    /**
     * Delete the "id" optionset.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
}
