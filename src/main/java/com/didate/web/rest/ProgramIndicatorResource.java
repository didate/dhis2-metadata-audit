package com.didate.web.rest;

import com.didate.domain.ProgramIndicator;
import com.didate.repository.ProgramIndicatorRepository;
import com.didate.service.ProgramIndicatorService;
import com.didate.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link com.didate.domain.ProgramIndicator}.
 */
@RestController
@RequestMapping("/api")
public class ProgramIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(ProgramIndicatorResource.class);

    private static final String ENTITY_NAME = "programIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramIndicatorService programIndicatorService;

    private final ProgramIndicatorRepository programIndicatorRepository;

    public ProgramIndicatorResource(
        ProgramIndicatorService programIndicatorService,
        ProgramIndicatorRepository programIndicatorRepository
    ) {
        this.programIndicatorService = programIndicatorService;
        this.programIndicatorRepository = programIndicatorRepository;
    }

    /**
     * {@code POST  /program-indicators} : Create a new programIndicator.
     *
     * @param programIndicator the programIndicator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programIndicator, or with status {@code 400 (Bad Request)} if the programIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/program-indicators")
    public ResponseEntity<ProgramIndicator> createProgramIndicator(@Valid @RequestBody ProgramIndicator programIndicator)
        throws URISyntaxException {
        log.debug("REST request to save ProgramIndicator : {}", programIndicator);
        if (programIndicator.getId() != null) {
            throw new BadRequestAlertException("A new programIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramIndicator result = programIndicatorService.save(programIndicator);
        return ResponseEntity
            .created(new URI("/api/program-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /program-indicators/:id} : Updates an existing programIndicator.
     *
     * @param id the id of the programIndicator to save.
     * @param programIndicator the programIndicator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programIndicator,
     * or with status {@code 400 (Bad Request)} if the programIndicator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programIndicator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/program-indicators/{id}")
    public ResponseEntity<ProgramIndicator> updateProgramIndicator(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ProgramIndicator programIndicator
    ) throws URISyntaxException {
        log.debug("REST request to update ProgramIndicator : {}, {}", id, programIndicator);
        if (programIndicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programIndicator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProgramIndicator result = programIndicatorService.update(programIndicator);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programIndicator.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /program-indicators/:id} : Partial updates given fields of an existing programIndicator, field will ignore if it is null
     *
     * @param id the id of the programIndicator to save.
     * @param programIndicator the programIndicator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programIndicator,
     * or with status {@code 400 (Bad Request)} if the programIndicator is not valid,
     * or with status {@code 404 (Not Found)} if the programIndicator is not found,
     * or with status {@code 500 (Internal Server Error)} if the programIndicator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/program-indicators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProgramIndicator> partialUpdateProgramIndicator(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ProgramIndicator programIndicator
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProgramIndicator partially : {}, {}", id, programIndicator);
        if (programIndicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programIndicator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramIndicator> result = programIndicatorService.partialUpdate(programIndicator);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programIndicator.getId())
        );
    }

    /**
     * {@code GET  /program-indicators} : get all the programIndicators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programIndicators in body.
     */
    @GetMapping("/program-indicators")
    public ResponseEntity<List<ProgramIndicator>> getAllProgramIndicators(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProgramIndicators");
        Page<ProgramIndicator> page = programIndicatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-indicators/:id} : get the "id" programIndicator.
     *
     * @param id the id of the programIndicator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programIndicator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-indicators/{id}")
    public ResponseEntity<ProgramIndicator> getProgramIndicator(@PathVariable String id) {
        log.debug("REST request to get ProgramIndicator : {}", id);
        Optional<ProgramIndicator> programIndicator = programIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programIndicator);
    }

    /**
     * {@code DELETE  /program-indicators/:id} : delete the "id" programIndicator.
     *
     * @param id the id of the programIndicator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/program-indicators/{id}")
    public ResponseEntity<Void> deleteProgramIndicator(@PathVariable String id) {
        log.debug("REST request to delete ProgramIndicator : {}", id);
        programIndicatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
