package com.didate.web.rest;

import com.didate.domain.Optionset;
import com.didate.repository.OptionsetRepository;
import com.didate.service.OptionsetService;
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
 * REST controller for managing {@link com.didate.domain.Optionset}.
 */
@RestController
@RequestMapping("/api")
public class OptionsetResource {

    private final Logger log = LoggerFactory.getLogger(OptionsetResource.class);

    private static final String ENTITY_NAME = "optionset";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionsetService optionsetService;

    private final OptionsetRepository optionsetRepository;

    public OptionsetResource(OptionsetService optionsetService, OptionsetRepository optionsetRepository) {
        this.optionsetService = optionsetService;
        this.optionsetRepository = optionsetRepository;
    }

    /**
     * {@code POST  /optionsets} : Create a new optionset.
     *
     * @param optionset the optionset to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionset, or with status {@code 400 (Bad Request)} if the optionset has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/optionsets")
    public ResponseEntity<Optionset> createOptionset(@Valid @RequestBody Optionset optionset) throws URISyntaxException {
        log.debug("REST request to save Optionset : {}", optionset);
        if (optionset.getId() != null) {
            throw new BadRequestAlertException("A new optionset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optionset result = optionsetService.save(optionset);
        return ResponseEntity
            .created(new URI("/api/optionsets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /optionsets/:id} : Updates an existing optionset.
     *
     * @param id the id of the optionset to save.
     * @param optionset the optionset to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionset,
     * or with status {@code 400 (Bad Request)} if the optionset is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionset couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/optionsets/{id}")
    public ResponseEntity<Optionset> updateOptionset(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Optionset optionset
    ) throws URISyntaxException {
        log.debug("REST request to update Optionset : {}, {}", id, optionset);
        if (optionset.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionset.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionsetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optionset result = optionsetService.update(optionset);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionset.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /optionsets/:id} : Partial updates given fields of an existing optionset, field will ignore if it is null
     *
     * @param id the id of the optionset to save.
     * @param optionset the optionset to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionset,
     * or with status {@code 400 (Bad Request)} if the optionset is not valid,
     * or with status {@code 404 (Not Found)} if the optionset is not found,
     * or with status {@code 500 (Internal Server Error)} if the optionset couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/optionsets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Optionset> partialUpdateOptionset(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Optionset optionset
    ) throws URISyntaxException {
        log.debug("REST request to partial update Optionset partially : {}, {}", id, optionset);
        if (optionset.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionset.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionsetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Optionset> result = optionsetService.partialUpdate(optionset);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionset.getId())
        );
    }

    /**
     * {@code GET  /optionsets} : get all the optionsets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionsets in body.
     */
    @GetMapping("/optionsets")
    public ResponseEntity<List<Optionset>> getAllOptionsets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Optionsets");
        Page<Optionset> page = optionsetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /optionsets/:id} : get the "id" optionset.
     *
     * @param id the id of the optionset to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionset, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/optionsets/{id}")
    public ResponseEntity<Optionset> getOptionset(@PathVariable String id) {
        log.debug("REST request to get Optionset : {}", id);
        Optional<Optionset> optionset = optionsetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionset);
    }

    /**
     * {@code DELETE  /optionsets/:id} : delete the "id" optionset.
     *
     * @param id the id of the optionset to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/optionsets/{id}")
    public ResponseEntity<Void> deleteOptionset(@PathVariable String id) {
        log.debug("REST request to delete Optionset : {}", id);
        optionsetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
