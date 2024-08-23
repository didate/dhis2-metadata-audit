package com.didate.service;

import com.didate.domain.OptionGroup;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OptionGroup}.
 */
public interface OptionGroupService {
    /**
     * Save a optionGroup.
     *
     * @param optionGroup the entity to save.
     * @return the persisted entity.
     */
    OptionGroup save(OptionGroup optionGroup);

    /**
     * Updates a optionGroup.
     *
     * @param optionGroup the entity to update.
     * @return the persisted entity.
     */
    OptionGroup update(OptionGroup optionGroup);

    /**
     * Partially updates a optionGroup.
     *
     * @param optionGroup the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OptionGroup> partialUpdate(OptionGroup optionGroup);

    /**
     * Get all the optionGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionGroup> findAll(Pageable pageable);

    /**
     * Get the "id" optionGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionGroup> findOne(String id);

    /**
     * Delete the "id" optionGroup.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
