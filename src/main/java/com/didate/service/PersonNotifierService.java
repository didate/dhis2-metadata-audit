package com.didate.service;

import com.didate.domain.PersonNotifier;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PersonNotifier}.
 */
public interface PersonNotifierService {
    /**
     * Save a personNotifier.
     *
     * @param personNotifier the entity to save.
     * @return the persisted entity.
     */
    PersonNotifier save(PersonNotifier personNotifier);

    /**
     * Updates a personNotifier.
     *
     * @param personNotifier the entity to update.
     * @return the persisted entity.
     */
    PersonNotifier update(PersonNotifier personNotifier);

    /**
     * Partially updates a personNotifier.
     *
     * @param personNotifier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonNotifier> partialUpdate(PersonNotifier personNotifier);

    /**
     * Get all the personNotifiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonNotifier> findAll(Pageable pageable);

    /**
     * Get the "id" personNotifier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonNotifier> findOne(Long id);

    /**
     * Delete the "id" personNotifier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
