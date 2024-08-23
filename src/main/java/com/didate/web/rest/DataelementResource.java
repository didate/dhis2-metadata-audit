package com.didate.web.rest;

import com.didate.domain.Dataelement;
import com.didate.repository.DataelementRepository;
import com.didate.service.DataelementService;
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
 * REST controller for managing {@link com.didate.domain.Dataelement}.
 */
@RestController
@RequestMapping("/api")
public class DataelementResource {

    private final Logger log = LoggerFactory.getLogger(DataelementResource.class);

    private static final String ENTITY_NAME = "dataelement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataelementService dataelementService;

    private final DataelementRepository dataelementRepository;

    public DataelementResource(DataelementService dataelementService, DataelementRepository dataelementRepository) {
        this.dataelementService = dataelementService;
        this.dataelementRepository = dataelementRepository;
    }

    /**
     * {@code POST  /dataelements} : Create a new dataelement.
     *
     * @param dataelement the dataelement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataelement, or with status {@code 400 (Bad Request)} if the dataelement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dataelements")
    public ResponseEntity<Dataelement> createDataelement(@Valid @RequestBody Dataelement dataelement) throws URISyntaxException {
        log.debug("REST request to save Dataelement : {}", dataelement);
        if (dataelement.getId() != null) {
            throw new BadRequestAlertException("A new dataelement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dataelement result = dataelementService.save(dataelement);
        return ResponseEntity
            .created(new URI("/api/dataelements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /dataelements/:id} : Updates an existing dataelement.
     *
     * @param id the id of the dataelement to save.
     * @param dataelement the dataelement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataelement,
     * or with status {@code 400 (Bad Request)} if the dataelement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataelement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dataelements/{id}")
    public ResponseEntity<Dataelement> updateDataelement(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Dataelement dataelement
    ) throws URISyntaxException {
        log.debug("REST request to update Dataelement : {}, {}", id, dataelement);
        if (dataelement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataelement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataelementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Dataelement result = dataelementService.update(dataelement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataelement.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /dataelements/:id} : Partial updates given fields of an existing dataelement, field will ignore if it is null
     *
     * @param id the id of the dataelement to save.
     * @param dataelement the dataelement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataelement,
     * or with status {@code 400 (Bad Request)} if the dataelement is not valid,
     * or with status {@code 404 (Not Found)} if the dataelement is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataelement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dataelements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dataelement> partialUpdateDataelement(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Dataelement dataelement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dataelement partially : {}, {}", id, dataelement);
        if (dataelement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataelement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataelementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dataelement> result = dataelementService.partialUpdate(dataelement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataelement.getId())
        );
    }

    /**
     * {@code GET  /dataelements} : get all the dataelements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataelements in body.
     */
    @GetMapping("/dataelements")
    public ResponseEntity<List<Dataelement>> getAllDataelements(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Dataelements");
        Page<Dataelement> page = dataelementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dataelements/:id} : get the "id" dataelement.
     *
     * @param id the id of the dataelement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataelement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataelements/{id}")
    public ResponseEntity<Dataelement> getDataelement(@PathVariable String id) {
        log.debug("REST request to get Dataelement : {}", id);
        Optional<Dataelement> dataelement = dataelementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataelement);
    }

    /**
     * {@code DELETE  /dataelements/:id} : delete the "id" dataelement.
     *
     * @param id the id of the dataelement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dataelements/{id}")
    public ResponseEntity<Void> deleteDataelement(@PathVariable String id) {
        log.debug("REST request to delete Dataelement : {}", id);
        dataelementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
