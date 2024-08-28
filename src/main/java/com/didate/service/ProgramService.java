package com.didate.service;

import com.didate.domain.Program;
import com.didate.service.dto.ProgramDTO;
import com.didate.service.dto.ProgramFullDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Program}.
 */
public interface ProgramService {
    /**
     * Save a program.
     *
     * @param program the entity to save.
     * @return the persisted entity.
     */
    Program save(Program program);

    /**
     * Updates a program.
     *
     * @param program the entity to update.
     * @return the persisted entity.
     */
    Program update(Program program);

    /**
     * Partially updates a program.
     *
     * @param program the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Program> partialUpdate(Program program);

    /**
     * Get all the programs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Program> findAll(Pageable pageable);

    /**
     * Get all the programs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramDTO> findAllPrograms(Pageable pageable);

    /**
     * Get all the programs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Program> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" program.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Program> findOne(String id);

    /**
     * Delete the "id" program.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    Boolean exist(String id);
    Long count();

    List<ProgramDTO> findAudits(String id);
    ProgramFullDTO findAuditRevision(String id, Integer rev);
}
