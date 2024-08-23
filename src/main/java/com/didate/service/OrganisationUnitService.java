package com.didate.service;

import com.didate.domain.OrganisationUnit;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OrganisationUnit}.
 */
public interface OrganisationUnitService {
    /**
     * Save a organisationUnit.
     *
     * @param organisationUnit the entity to save.
     * @return the persisted entity.
     */
    OrganisationUnit save(OrganisationUnit organisationUnit);

    /**
     * Updates a organisationUnit.
     *
     * @param organisationUnit the entity to update.
     * @return the persisted entity.
     */
    OrganisationUnit update(OrganisationUnit organisationUnit);

    /**
     * Partially updates a organisationUnit.
     *
     * @param organisationUnit the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganisationUnit> partialUpdate(OrganisationUnit organisationUnit);

    /**
     * Get all the organisationUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganisationUnit> findAll(Pageable pageable);

    /**
     * Get the "id" organisationUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganisationUnit> findOne(String id);

    /**
     * Delete the "id" organisationUnit.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
}
