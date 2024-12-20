package com.didate.service;

import com.didate.domain.ProgramIndicator;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.dto.ProgramIndicatorDTO;
import com.didate.service.dto.ProgramIndicatorFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramIndicator}.
 */
public interface ProgramIndicatorService {
    /**
     * Save a programIndicator.
     *
     * @param programIndicator the entity to save.
     * @return the persisted entity.
     */
    ProgramIndicator save(ProgramIndicator programIndicator);

    /**
     * Updates a programIndicator.
     *
     * @param programIndicator the entity to update.
     * @return the persisted entity.
     */
    ProgramIndicator update(ProgramIndicator programIndicator);

    /**
     * Partially updates a programIndicator.
     *
     * @param programIndicator the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramIndicator> partialUpdate(ProgramIndicator programIndicator);

    /**
     * Get all the programIndicators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramIndicator> findAll(Pageable pageable);

    public Page<ProgramIndicatorDTO> findAll(Pageable pageable, String id, String name);

    /**
     * Get the "id" programIndicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramIndicator> findOne(String id);

    /**
     * Delete the "id" programIndicator.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();
    long countByTrack(TypeTrack track);

    void setTrackNone();

    List<ProgramIndicatorDTO> findAudits(String id);
    ProgramIndicatorFullDTO findAuditRevision(String id, Integer rev);
}
