package com.didate.service;

import com.didate.domain.DHISUser;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.dto.DHISUserDTO;
import com.didate.service.dto.DHISUserFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DHISUser}.
 */
public interface DHISUserService {
    /**
     * Save a dHISUser.
     *
     * @param dHISUser the entity to save.
     * @return the persisted entity.
     */
    DHISUser save(DHISUser dHISUser);

    /**
     * Updates a dHISUser.
     *
     * @param dHISUser the entity to update.
     * @return the persisted entity.
     */
    DHISUser update(DHISUser dHISUser);

    /**
     * Partially updates a dHISUser.
     *
     * @param dHISUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DHISUser> partialUpdate(DHISUser dHISUser);

    /**
     * Get all the dHISUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DHISUser> findAll(Pageable pageable);

    public Page<DHISUserDTO> findAll(Pageable pageable, String id, String name, String username, Integer months, Boolean disabled);

    /**
     * Get the "id" dHISUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DHISUser> findOne(String id);

    /**
     * Delete the "id" dHISUser.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
    long countByTrack(TypeTrack track);

    void setTrackNone();

    List<DHISUserDTO> findAudits(String id);
    DHISUserFullDTO findAuditRevision(String id, Integer rev);
}
