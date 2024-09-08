package com.didate.service;

import com.didate.domain.DataSet;
import com.didate.service.dto.DataSetDTO;
import com.didate.service.dto.DataSetFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DataSet}.
 */
public interface DatasetService {
    /**
     * Save a dataset.
     *
     * @param dataset the entity to save.
     * @return the persisted entity.
     */
    DataSet save(DataSet dataset);

    /**
     * Updates a dataset.
     *
     * @param dataset the entity to update.
     * @return the persisted entity.
     */
    DataSet update(DataSet dataset);

    /**
     * Partially updates a dataset.
     *
     * @param dataset the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DataSet> partialUpdate(DataSet dataset);

    /**
     * Get all the datasets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataSet> findAll(Pageable pageable);

    public Page<DataSetDTO> findAll(Pageable pageable, String id, String name);

    Page<DataSetDTO> findAllDataSets(Pageable pageable);

    /**
     * Get all the datasets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataSet> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dataset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataSet> findOne(String id);

    /**
     * Delete the "id" dataset.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();

    List<DataSetDTO> findAudits(String id);
    DataSetFullDTO findAuditRevision(String id, Integer rev);
}
