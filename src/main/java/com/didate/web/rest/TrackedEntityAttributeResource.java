package com.didate.web.rest;

import com.didate.domain.TrackedEntityAttribute;
import com.didate.repository.TrackedEntityAttributeRepository;
import com.didate.service.TrackedEntityAttributeService;
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
 * REST controller for managing {@link com.didate.domain.TrackedEntityAttribute}.
 */
@RestController
@RequestMapping("/api/tracked-entity-attributes")
public class TrackedEntityAttributeResource {

    private static final Logger log = LoggerFactory.getLogger(TrackedEntityAttributeResource.class);

    private static final String ENTITY_NAME = "trackedEntityAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrackedEntityAttributeService trackedEntityAttributeService;

    private final TrackedEntityAttributeRepository trackedEntityAttributeRepository;

    public TrackedEntityAttributeResource(
        TrackedEntityAttributeService trackedEntityAttributeService,
        TrackedEntityAttributeRepository trackedEntityAttributeRepository
    ) {
        this.trackedEntityAttributeService = trackedEntityAttributeService;
        this.trackedEntityAttributeRepository = trackedEntityAttributeRepository;
    }

    /**
     * {@code POST  /tracked-entity-attributes} : Create a new trackedEntityAttribute.
     *
     * @param trackedEntityAttribute the trackedEntityAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trackedEntityAttribute, or with status {@code 400 (Bad Request)} if the trackedEntityAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TrackedEntityAttribute> createTrackedEntityAttribute(
        @Valid @RequestBody TrackedEntityAttribute trackedEntityAttribute
    ) throws URISyntaxException {
        log.debug("REST request to save TrackedEntityAttribute : {}", trackedEntityAttribute);
        if (trackedEntityAttribute.getId() != null) {
            throw new BadRequestAlertException("A new trackedEntityAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trackedEntityAttribute = trackedEntityAttributeService.save(trackedEntityAttribute);
        return ResponseEntity.created(new URI("/api/tracked-entity-attributes/" + trackedEntityAttribute.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, trackedEntityAttribute.getId()))
            .body(trackedEntityAttribute);
    }

    /**
     * {@code PUT  /tracked-entity-attributes/:id} : Updates an existing trackedEntityAttribute.
     *
     * @param id the id of the trackedEntityAttribute to save.
     * @param trackedEntityAttribute the trackedEntityAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trackedEntityAttribute,
     * or with status {@code 400 (Bad Request)} if the trackedEntityAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trackedEntityAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrackedEntityAttribute> updateTrackedEntityAttribute(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody TrackedEntityAttribute trackedEntityAttribute
    ) throws URISyntaxException {
        log.debug("REST request to update TrackedEntityAttribute : {}, {}", id, trackedEntityAttribute);
        if (trackedEntityAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trackedEntityAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trackedEntityAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trackedEntityAttribute = trackedEntityAttributeService.update(trackedEntityAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trackedEntityAttribute.getId()))
            .body(trackedEntityAttribute);
    }

    /**
     * {@code PATCH  /tracked-entity-attributes/:id} : Partial updates given fields of an existing trackedEntityAttribute, field will ignore if it is null
     *
     * @param id the id of the trackedEntityAttribute to save.
     * @param trackedEntityAttribute the trackedEntityAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trackedEntityAttribute,
     * or with status {@code 400 (Bad Request)} if the trackedEntityAttribute is not valid,
     * or with status {@code 404 (Not Found)} if the trackedEntityAttribute is not found,
     * or with status {@code 500 (Internal Server Error)} if the trackedEntityAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrackedEntityAttribute> partialUpdateTrackedEntityAttribute(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody TrackedEntityAttribute trackedEntityAttribute
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrackedEntityAttribute partially : {}, {}", id, trackedEntityAttribute);
        if (trackedEntityAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trackedEntityAttribute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trackedEntityAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrackedEntityAttribute> result = trackedEntityAttributeService.partialUpdate(trackedEntityAttribute);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trackedEntityAttribute.getId())
        );
    }

    /**
     * {@code GET  /tracked-entity-attributes} : get all the trackedEntityAttributes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trackedEntityAttributes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TrackedEntityAttribute>> getAllTrackedEntityAttributes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TrackedEntityAttributes");
        Page<TrackedEntityAttribute> page = trackedEntityAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tracked-entity-attributes/:id} : get the "id" trackedEntityAttribute.
     *
     * @param id the id of the trackedEntityAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trackedEntityAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrackedEntityAttribute> getTrackedEntityAttribute(@PathVariable("id") String id) {
        log.debug("REST request to get TrackedEntityAttribute : {}", id);
        Optional<TrackedEntityAttribute> trackedEntityAttribute = trackedEntityAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trackedEntityAttribute);
    }

    /**
     * {@code DELETE  /tracked-entity-attributes/:id} : delete the "id" trackedEntityAttribute.
     *
     * @param id the id of the trackedEntityAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrackedEntityAttribute(@PathVariable("id") String id) {
        log.debug("REST request to delete TrackedEntityAttribute : {}", id);
        trackedEntityAttributeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
