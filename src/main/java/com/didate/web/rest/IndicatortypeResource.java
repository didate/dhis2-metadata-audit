package com.didate.web.rest;

import com.didate.domain.Indicatortype;
import com.didate.repository.IndicatortypeRepository;
import com.didate.service.IndicatortypeService;
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
 * REST controller for managing {@link com.didate.domain.Indicatortype}.
 */
@RestController
@RequestMapping("/api")
public class IndicatortypeResource {

    private final Logger log = LoggerFactory.getLogger(IndicatortypeResource.class);

    private static final String ENTITY_NAME = "indicatortype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicatortypeService indicatortypeService;

    private final IndicatortypeRepository indicatortypeRepository;

    public IndicatortypeResource(IndicatortypeService indicatortypeService, IndicatortypeRepository indicatortypeRepository) {
        this.indicatortypeService = indicatortypeService;
        this.indicatortypeRepository = indicatortypeRepository;
    }

    /**
     * {@code POST  /indicatortypes} : Create a new indicatortype.
     *
     * @param indicatortype the indicatortype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicatortype, or with status {@code 400 (Bad Request)} if the indicatortype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/indicatortypes")
    public ResponseEntity<Indicatortype> createIndicatortype(@Valid @RequestBody Indicatortype indicatortype) throws URISyntaxException {
        log.debug("REST request to save Indicatortype : {}", indicatortype);
        if (indicatortype.getId() != null) {
            throw new BadRequestAlertException("A new indicatortype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Indicatortype result = indicatortypeService.save(indicatortype);
        return ResponseEntity
            .created(new URI("/api/indicatortypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /indicatortypes/:id} : Updates an existing indicatortype.
     *
     * @param id the id of the indicatortype to save.
     * @param indicatortype the indicatortype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicatortype,
     * or with status {@code 400 (Bad Request)} if the indicatortype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicatortype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/indicatortypes/{id}")
    public ResponseEntity<Indicatortype> updateIndicatortype(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Indicatortype indicatortype
    ) throws URISyntaxException {
        log.debug("REST request to update Indicatortype : {}, {}", id, indicatortype);
        if (indicatortype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicatortype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicatortypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Indicatortype result = indicatortypeService.update(indicatortype);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicatortype.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /indicatortypes/:id} : Partial updates given fields of an existing indicatortype, field will ignore if it is null
     *
     * @param id the id of the indicatortype to save.
     * @param indicatortype the indicatortype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicatortype,
     * or with status {@code 400 (Bad Request)} if the indicatortype is not valid,
     * or with status {@code 404 (Not Found)} if the indicatortype is not found,
     * or with status {@code 500 (Internal Server Error)} if the indicatortype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/indicatortypes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Indicatortype> partialUpdateIndicatortype(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Indicatortype indicatortype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Indicatortype partially : {}, {}", id, indicatortype);
        if (indicatortype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicatortype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicatortypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Indicatortype> result = indicatortypeService.partialUpdate(indicatortype);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicatortype.getId())
        );
    }

    /**
     * {@code GET  /indicatortypes} : get all the indicatortypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicatortypes in body.
     */
    @GetMapping("/indicatortypes")
    public ResponseEntity<List<Indicatortype>> getAllIndicatortypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Indicatortypes");
        Page<Indicatortype> page = indicatortypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /indicatortypes/:id} : get the "id" indicatortype.
     *
     * @param id the id of the indicatortype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicatortype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indicatortypes/{id}")
    public ResponseEntity<Indicatortype> getIndicatortype(@PathVariable String id) {
        log.debug("REST request to get Indicatortype : {}", id);
        Optional<Indicatortype> indicatortype = indicatortypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicatortype);
    }

    /**
     * {@code DELETE  /indicatortypes/:id} : delete the "id" indicatortype.
     *
     * @param id the id of the indicatortype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/indicatortypes/{id}")
    public ResponseEntity<Void> deleteIndicatortype(@PathVariable String id) {
        log.debug("REST request to delete Indicatortype : {}", id);
        indicatortypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
