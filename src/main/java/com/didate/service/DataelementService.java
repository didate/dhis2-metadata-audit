package com.didate.service;

import com.didate.domain.DataElement;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.dto.DataElementDTO;
import com.didate.service.dto.DataElementFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DataElement}.
 */
public interface DataelementService {
    /**
     * Save a dataelement.
     *
     * @param dataelement the entity to save.
     * @return the persisted entity.
     */
    DataElement save(DataElement dataelement);

    /**
     * Updates a dataelement.
     *
     * @param dataelement the entity to update.
     * @return the persisted entity.
     */
    DataElement update(DataElement dataelement);

    /**
     * Partially updates a dataelement.
     *
     * @param dataelement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DataElement> partialUpdate(DataElement dataelement);

    /**
     * Get all the dataelements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataElement> findAll(Pageable pageable);

    public Page<DataElementDTO> findAll(Pageable pageable, String id, String name);

    Page<DataElementDTO> findAllDataElements(Pageable pageable);

    /**
     * Get the "id" dataelement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataElement> findOne(String id);

    /**
     * Delete the "id" dataelement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
    long countByTrack(TypeTrack track);

    void setTrackNone();

    List<DataElementDTO> findAudits(String id);
    DataElementFullDTO findAuditRevision(String id, Integer rev);
}
