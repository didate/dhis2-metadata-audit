package com.didate.web.rest;

import com.didate.domain.ProgramRuleAction;
import com.didate.repository.ProgramRuleActionRepository;
import com.didate.service.ProgramRuleActionService;
import com.didate.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.didate.domain.ProgramRuleAction}.
 */
@RestController
@RequestMapping("/api/program-rule-actions")
public class ProgramRuleActionResource {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleActionResource.class);

    private static final String ENTITY_NAME = "programRuleAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramRuleActionService programRuleActionService;

    private final ProgramRuleActionRepository programRuleActionRepository;

    public ProgramRuleActionResource(
        ProgramRuleActionService programRuleActionService,
        ProgramRuleActionRepository programRuleActionRepository
    ) {
        this.programRuleActionService = programRuleActionService;
        this.programRuleActionRepository = programRuleActionRepository;
    }

    /**
     * {@code POST  /program-rule-actions} : Create a new programRuleAction.
     *
     * @param programRuleAction the programRuleAction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programRuleAction, or with status {@code 400 (Bad Request)} if the programRuleAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProgramRuleAction> createProgramRuleAction(@Valid @RequestBody ProgramRuleAction programRuleAction)
        throws URISyntaxException {
        log.debug("REST request to save ProgramRuleAction : {}", programRuleAction);
        if (programRuleAction.getId() != null) {
            throw new BadRequestAlertException("A new programRuleAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        programRuleAction = programRuleActionService.save(programRuleAction);
        return ResponseEntity.created(new URI("/api/program-rule-actions/" + programRuleAction.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, programRuleAction.getId()))
            .body(programRuleAction);
    }

    /**
     * {@code PUT  /program-rule-actions/:id} : Updates an existing programRuleAction.
     *
     * @param id the id of the programRuleAction to save.
     * @param programRuleAction the programRuleAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programRuleAction,
     * or with status {@code 400 (Bad Request)} if the programRuleAction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programRuleAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgramRuleAction> updateProgramRuleAction(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ProgramRuleAction programRuleAction
    ) throws URISyntaxException {
        log.debug("REST request to update ProgramRuleAction : {}, {}", id, programRuleAction);
        if (programRuleAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programRuleAction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRuleActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        programRuleAction = programRuleActionService.update(programRuleAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programRuleAction.getId()))
            .body(programRuleAction);
    }

    /**
     * {@code PATCH  /program-rule-actions/:id} : Partial updates given fields of an existing programRuleAction, field will ignore if it is null
     *
     * @param id the id of the programRuleAction to save.
     * @param programRuleAction the programRuleAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programRuleAction,
     * or with status {@code 400 (Bad Request)} if the programRuleAction is not valid,
     * or with status {@code 404 (Not Found)} if the programRuleAction is not found,
     * or with status {@code 500 (Internal Server Error)} if the programRuleAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProgramRuleAction> partialUpdateProgramRuleAction(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ProgramRuleAction programRuleAction
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProgramRuleAction partially : {}, {}", id, programRuleAction);
        if (programRuleAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programRuleAction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRuleActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramRuleAction> result = programRuleActionService.partialUpdate(programRuleAction);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programRuleAction.getId())
        );
    }

    /**
     * {@code GET  /program-rule-actions} : get all the programRuleActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programRuleActions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProgramRuleAction>> getAllProgramRuleActions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProgramRuleActions");
        Page<ProgramRuleAction> page = programRuleActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-rule-actions/:id} : get the "id" programRuleAction.
     *
     * @param id the id of the programRuleAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programRuleAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgramRuleAction> getProgramRuleAction(@PathVariable("id") String id) {
        log.debug("REST request to get ProgramRuleAction : {}", id);
        Optional<ProgramRuleAction> programRuleAction = programRuleActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programRuleAction);
    }

    /**
     * {@code DELETE  /program-rule-actions/:id} : delete the "id" programRuleAction.
     *
     * @param id the id of the programRuleAction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramRuleAction(@PathVariable("id") String id) {
        log.debug("REST request to delete ProgramRuleAction : {}", id);
        programRuleActionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
