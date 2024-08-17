package com.didate.service;

import com.didate.domain.Optionset;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.didate.domain.Optionset}.
 */
public interface OptionsetService {
    /**
     * Save a optionset.
     *
     * @param optionset the entity to save.
     * @return the persisted entity.
     */
    Optionset save(Optionset optionset);

    /**
     * Updates a optionset.
     *
     * @param optionset the entity to update.
     * @return the persisted entity.
     */
    Optionset update(Optionset optionset);

    /**
     * Partially updates a optionset.
     *
     * @param optionset the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Optionset> partialUpdate(Optionset optionset);

    /**
     * Get all the optionsets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Optionset> findAll(Pageable pageable);

    /**
     * Get the "id" optionset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Optionset> findOne(String id);

    /**
     * Delete the "id" optionset.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
