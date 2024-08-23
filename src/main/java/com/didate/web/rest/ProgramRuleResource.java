package com.didate.web.rest;

import com.didate.domain.ProgramRule;
import com.didate.repository.ProgramRuleRepository;
import com.didate.service.ProgramRuleService;
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
 * REST controller for managing {@link com.didate.domain.ProgramRule}.
 */
@RestController
@RequestMapping("/api/program-rules")
public class ProgramRuleResource {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleResource.class);

    private static final String ENTITY_NAME = "programRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramRuleService programRuleService;

    private final ProgramRuleRepository programRuleRepository;

    public ProgramRuleResource(ProgramRuleService programRuleService, ProgramRuleRepository programRuleRepository) {
        this.programRuleService = programRuleService;
        this.programRuleRepository = programRuleRepository;
    }

    /**
     * {@code POST  /program-rules} : Create a new programRule.
     *
     * @param programRule the programRule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programRule, or with status {@code 400 (Bad Request)} if the programRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProgramRule> createProgramRule(@Valid @RequestBody ProgramRule programRule) throws URISyntaxException {
        log.debug("REST request to save ProgramRule : {}", programRule);
        if (programRule.getId() != null) {
            throw new BadRequestAlertException("A new programRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        programRule = programRuleService.save(programRule);
        return ResponseEntity.created(new URI("/api/program-rules/" + programRule.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, programRule.getId()))
            .body(programRule);
    }

    /**
     * {@code PUT  /program-rules/:id} : Updates an existing programRule.
     *
     * @param id the id of the programRule to save.
     * @param programRule the programRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programRule,
     * or with status {@code 400 (Bad Request)} if the programRule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgramRule> updateProgramRule(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ProgramRule programRule
    ) throws URISyntaxException {
        log.debug("REST request to update ProgramRule : {}, {}", id, programRule);
        if (programRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programRule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        programRule = programRuleService.update(programRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programRule.getId()))
            .body(programRule);
    }

    /**
     * {@code PATCH  /program-rules/:id} : Partial updates given fields of an existing programRule, field will ignore if it is null
     *
     * @param id the id of the programRule to save.
     * @param programRule the programRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programRule,
     * or with status {@code 400 (Bad Request)} if the programRule is not valid,
     * or with status {@code 404 (Not Found)} if the programRule is not found,
     * or with status {@code 500 (Internal Server Error)} if the programRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProgramRule> partialUpdateProgramRule(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ProgramRule programRule
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProgramRule partially : {}, {}", id, programRule);
        if (programRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programRule.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramRule> result = programRuleService.partialUpdate(programRule);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programRule.getId())
        );
    }

    /**
     * {@code GET  /program-rules} : get all the programRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programRules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProgramRule>> getAllProgramRules(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProgramRules");
        Page<ProgramRule> page = programRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-rules/:id} : get the "id" programRule.
     *
     * @param id the id of the programRule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programRule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgramRule> getProgramRule(@PathVariable("id") String id) {
        log.debug("REST request to get ProgramRule : {}", id);
        Optional<ProgramRule> programRule = programRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programRule);
    }

    /**
     * {@code DELETE  /program-rules/:id} : delete the "id" programRule.
     *
     * @param id the id of the programRule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramRule(@PathVariable("id") String id) {
        log.debug("REST request to delete ProgramRule : {}", id);
        programRuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
