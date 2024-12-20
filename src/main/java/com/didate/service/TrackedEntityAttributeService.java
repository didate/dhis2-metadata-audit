package com.didate.service;

import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.dto.TrackedEntityAttributeDTO;
import com.didate.service.dto.TrackedEntityAttributeFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TrackedEntityAttribute}.
 */
public interface TrackedEntityAttributeService {
    /**
     * Save a trackedEntityAttribute.
     *
     * @param trackedEntityAttribute the entity to save.
     * @return the persisted entity.
     */
    TrackedEntityAttribute save(TrackedEntityAttribute trackedEntityAttribute);

    /**
     * Updates a trackedEntityAttribute.
     *
     * @param trackedEntityAttribute the entity to update.
     * @return the persisted entity.
     */
    TrackedEntityAttribute update(TrackedEntityAttribute trackedEntityAttribute);

    /**
     * Partially updates a trackedEntityAttribute.
     *
     * @param trackedEntityAttribute the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrackedEntityAttribute> partialUpdate(TrackedEntityAttribute trackedEntityAttribute);

    /**
     * Get all the trackedEntityAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrackedEntityAttribute> findAll(Pageable pageable);

    public Page<TrackedEntityAttributeDTO> findAll(Pageable pageable, String id, String name);

    /**
     * Get the "id" trackedEntityAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrackedEntityAttribute> findOne(String id);

    /**
     * Delete the "id" trackedEntityAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
    long countByTrack(TypeTrack track);

    void setTrackNone();

    List<TrackedEntityAttributeDTO> findAudits(String id);
    TrackedEntityAttributeFullDTO findAuditRevision(String id, Integer rev);
}
