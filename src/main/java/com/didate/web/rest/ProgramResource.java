package com.didate.web.rest;

import com.didate.domain.Program;
import com.didate.repository.ProgramRepository;
import com.didate.service.ProgramService;
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
 * REST controller for managing {@link com.didate.domain.Program}.
 */
@RestController
@RequestMapping("/api/programs")
public class ProgramResource {

    private static final Logger log = LoggerFactory.getLogger(ProgramResource.class);

    private static final String ENTITY_NAME = "program";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramService programService;

    private final ProgramRepository programRepository;

    public ProgramResource(ProgramService programService, ProgramRepository programRepository) {
        this.programService = programService;
        this.programRepository = programRepository;
    }

    /**
     * {@code POST  /programs} : Create a new program.
     *
     * @param program the program to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new program, or with status {@code 400 (Bad Request)} if the program has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Program> createProgram(@Valid @RequestBody Program program) throws URISyntaxException {
        log.debug("REST request to save Program : {}", program);
        if (program.getId() != null) {
            throw new BadRequestAlertException("A new program cannot already have an ID", ENTITY_NAME, "idexists");
        }
        program = programService.save(program);
        return ResponseEntity.created(new URI("/api/programs/" + program.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, program.getId()))
            .body(program);
    }

    /**
     * {@code PUT  /programs/:id} : Updates an existing program.
     *
     * @param id the id of the program to save.
     * @param program the program to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated program,
     * or with status {@code 400 (Bad Request)} if the program is not valid,
     * or with status {@code 500 (Internal Server Error)} if the program couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Program program
    ) throws URISyntaxException {
        log.debug("REST request to update Program : {}, {}", id, program);
        if (program.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, program.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        program = programService.update(program);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, program.getId()))
            .body(program);
    }

    /**
     * {@code PATCH  /programs/:id} : Partial updates given fields of an existing program, field will ignore if it is null
     *
     * @param id the id of the program to save.
     * @param program the program to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated program,
     * or with status {@code 400 (Bad Request)} if the program is not valid,
     * or with status {@code 404 (Not Found)} if the program is not found,
     * or with status {@code 500 (Internal Server Error)} if the program couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Program> partialUpdateProgram(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Program program
    ) throws URISyntaxException {
        log.debug("REST request to partial update Program partially : {}, {}", id, program);
        if (program.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, program.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Program> result = programService.partialUpdate(program);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, program.getId()));
    }

    /**
     * {@code GET  /programs} : get all the programs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Program>> getAllPrograms(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Programs");
        Page<Program> page = programService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /programs/:id} : get the "id" program.
     *
     * @param id the id of the program to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the program, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable("id") String id) {
        log.debug("REST request to get Program : {}", id);
        Optional<Program> program = programService.findOne(id);
        return ResponseUtil.wrapOrNotFound(program);
    }

    /**
     * {@code DELETE  /programs/:id} : delete the "id" program.
     *
     * @param id the id of the program to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable("id") String id) {
        log.debug("REST request to delete Program : {}", id);
        programService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
