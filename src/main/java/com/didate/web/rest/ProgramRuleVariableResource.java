package com.didate.web.rest;

import com.didate.domain.ProgramRuleVariable;
import com.didate.repository.ProgramRuleVariableRepository;
import com.didate.service.ProgramRuleVariableService;
import com.didate.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.didate.domain.ProgramRuleVariable}.
 */
@RestController
@RequestMapping("/api")
public class ProgramRuleVariableResource {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleVariableResource.class);

    private static final String ENTITY_NAME = "programRuleVariable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramRuleVariableService programRuleVariableService;

    private final ProgramRuleVariableRepository programRuleVariableRepository;

    public ProgramRuleVariableResource(
        ProgramRuleVariableService programRuleVariableService,
        ProgramRuleVariableRepository programRuleVariableRepository
    ) {
        this.programRuleVariableService = programRuleVariableService;
        this.programRuleVariableRepository = programRuleVariableRepository;
    }

    /**
     * {@code POST  /program-rule-variables} : Create a new programRuleVariable.
     *
     * @param programRuleVariable the programRuleVariable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programRuleVariable, or with status {@code 400 (Bad Request)} if the programRuleVariable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/program-rule-variables")
    public ResponseEntity<ProgramRuleVariable> createProgramRuleVariable(@Valid @RequestBody ProgramRuleVariable programRuleVariable)
        throws URISyntaxException {
        log.debug("REST request to save ProgramRuleVariable : {}", programRuleVariable);
        if (programRuleVariable.getId() != null) {
            throw new BadRequestAlertException("A new programRuleVariable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramRuleVariable result = programRuleVariableService.save(programRuleVariable);
        return ResponseEntity
            .created(new URI("/api/program-rule-variables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /program-rule-variables/:id} : Updates an existing programRuleVariable.
     *
     * @param id the id of the programRuleVariable to save.
     * @param programRuleVariable the programRuleVariable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programRuleVariable,
     * or with status {@code 400 (Bad Request)} if the programRuleVariable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programRuleVariable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/program-rule-variables/{id}")
    public ResponseEntity<ProgramRuleVariable> updateProgramRuleVariable(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ProgramRuleVariable programRuleVariable
    ) throws URISyntaxException {
        log.debug("REST request to update ProgramRuleVariable : {}, {}", id, programRuleVariable);
        if (programRuleVariable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programRuleVariable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRuleVariableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProgramRuleVariable result = programRuleVariableService.update(programRuleVariable);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programRuleVariable.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /program-rule-variables/:id} : Partial updates given fields of an existing programRuleVariable, field will ignore if it is null
     *
     * @param id the id of the programRuleVariable to save.
     * @param programRuleVariable the programRuleVariable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programRuleVariable,
     * or with status {@code 400 (Bad Request)} if the programRuleVariable is not valid,
     * or with status {@code 404 (Not Found)} if the programRuleVariable is not found,
     * or with status {@code 500 (Internal Server Error)} if the programRuleVariable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/program-rule-variables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProgramRuleVariable> partialUpdateProgramRuleVariable(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ProgramRuleVariable programRuleVariable
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProgramRuleVariable partially : {}, {}", id, programRuleVariable);
        if (programRuleVariable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programRuleVariable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRuleVariableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramRuleVariable> result = programRuleVariableService.partialUpdate(programRuleVariable);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programRuleVariable.getId())
        );
    }

    /**
     * {@code GET  /program-rule-variables} : get all the programRuleVariables.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programRuleVariables in body.
     */
    @GetMapping("/program-rule-variables")
    public ResponseEntity<List<ProgramRuleVariable>> getAllProgramRuleVariables(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProgramRuleVariables");
        Page<ProgramRuleVariable> page = programRuleVariableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-rule-variables/:id} : get the "id" programRuleVariable.
     *
     * @param id the id of the programRuleVariable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programRuleVariable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-rule-variables/{id}")
    public ResponseEntity<ProgramRuleVariable> getProgramRuleVariable(@PathVariable String id) {
        log.debug("REST request to get ProgramRuleVariable : {}", id);
        Optional<ProgramRuleVariable> programRuleVariable = programRuleVariableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programRuleVariable);
    }

    /**
     * {@code DELETE  /program-rule-variables/:id} : delete the "id" programRuleVariable.
     *
     * @param id the id of the programRuleVariable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/program-rule-variables/{id}")
    public ResponseEntity<Void> deleteProgramRuleVariable(@PathVariable String id) {
        log.debug("REST request to delete ProgramRuleVariable : {}", id);
        programRuleVariableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
