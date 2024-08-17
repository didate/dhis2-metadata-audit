package com.didate.web.rest;

import com.didate.domain.Indicator;
import com.didate.repository.IndicatorRepository;
import com.didate.service.IndicatorService;
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
 * REST controller for managing {@link com.didate.domain.Indicator}.
 */
@RestController
@RequestMapping("/api/indicators")
public class IndicatorResource {

    private static final Logger log = LoggerFactory.getLogger(IndicatorResource.class);

    private static final String ENTITY_NAME = "indicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicatorService indicatorService;

    private final IndicatorRepository indicatorRepository;

    public IndicatorResource(IndicatorService indicatorService, IndicatorRepository indicatorRepository) {
        this.indicatorService = indicatorService;
        this.indicatorRepository = indicatorRepository;
    }

    /**
     * {@code POST  /indicators} : Create a new indicator.
     *
     * @param indicator the indicator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicator, or with status {@code 400 (Bad Request)} if the indicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Indicator> createIndicator(@Valid @RequestBody Indicator indicator) throws URISyntaxException {
        log.debug("REST request to save Indicator : {}", indicator);
        if (indicator.getId() != null) {
            throw new BadRequestAlertException("A new indicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        indicator = indicatorService.save(indicator);
        return ResponseEntity.created(new URI("/api/indicators/" + indicator.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, indicator.getId()))
            .body(indicator);
    }

    /**
     * {@code PUT  /indicators/:id} : Updates an existing indicator.
     *
     * @param id the id of the indicator to save.
     * @param indicator the indicator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicator,
     * or with status {@code 400 (Bad Request)} if the indicator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Indicator> updateIndicator(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Indicator indicator
    ) throws URISyntaxException {
        log.debug("REST request to update Indicator : {}, {}", id, indicator);
        if (indicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        indicator = indicatorService.update(indicator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicator.getId()))
            .body(indicator);
    }

    /**
     * {@code PATCH  /indicators/:id} : Partial updates given fields of an existing indicator, field will ignore if it is null
     *
     * @param id the id of the indicator to save.
     * @param indicator the indicator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicator,
     * or with status {@code 400 (Bad Request)} if the indicator is not valid,
     * or with status {@code 404 (Not Found)} if the indicator is not found,
     * or with status {@code 500 (Internal Server Error)} if the indicator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Indicator> partialUpdateIndicator(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Indicator indicator
    ) throws URISyntaxException {
        log.debug("REST request to partial update Indicator partially : {}, {}", id, indicator);
        if (indicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Indicator> result = indicatorService.partialUpdate(indicator);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indicator.getId())
        );
    }

    /**
     * {@code GET  /indicators} : get all the indicators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicators in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Indicator>> getAllIndicators(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Indicators");
        Page<Indicator> page = indicatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /indicators/:id} : get the "id" indicator.
     *
     * @param id the id of the indicator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Indicator> getIndicator(@PathVariable("id") String id) {
        log.debug("REST request to get Indicator : {}", id);
        Optional<Indicator> indicator = indicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicator);
    }

    /**
     * {@code DELETE  /indicators/:id} : delete the "id" indicator.
     *
     * @param id the id of the indicator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndicator(@PathVariable("id") String id) {
        log.debug("REST request to delete Indicator : {}", id);
        indicatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
