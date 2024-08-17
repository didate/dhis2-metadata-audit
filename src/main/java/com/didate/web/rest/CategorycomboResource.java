package com.didate.web.rest;

import com.didate.domain.Categorycombo;
import com.didate.repository.CategorycomboRepository;
import com.didate.service.CategorycomboService;
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
 * REST controller for managing {@link com.didate.domain.Categorycombo}.
 */
@RestController
@RequestMapping("/api/categorycombos")
public class CategorycomboResource {

    private static final Logger log = LoggerFactory.getLogger(CategorycomboResource.class);

    private static final String ENTITY_NAME = "categorycombo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorycomboService categorycomboService;

    private final CategorycomboRepository categorycomboRepository;

    public CategorycomboResource(CategorycomboService categorycomboService, CategorycomboRepository categorycomboRepository) {
        this.categorycomboService = categorycomboService;
        this.categorycomboRepository = categorycomboRepository;
    }

    /**
     * {@code POST  /categorycombos} : Create a new categorycombo.
     *
     * @param categorycombo the categorycombo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorycombo, or with status {@code 400 (Bad Request)} if the categorycombo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Categorycombo> createCategorycombo(@Valid @RequestBody Categorycombo categorycombo) throws URISyntaxException {
        log.debug("REST request to save Categorycombo : {}", categorycombo);
        if (categorycombo.getId() != null) {
            throw new BadRequestAlertException("A new categorycombo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        categorycombo = categorycomboService.save(categorycombo);
        return ResponseEntity.created(new URI("/api/categorycombos/" + categorycombo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, categorycombo.getId()))
            .body(categorycombo);
    }

    /**
     * {@code PUT  /categorycombos/:id} : Updates an existing categorycombo.
     *
     * @param id the id of the categorycombo to save.
     * @param categorycombo the categorycombo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorycombo,
     * or with status {@code 400 (Bad Request)} if the categorycombo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorycombo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Categorycombo> updateCategorycombo(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Categorycombo categorycombo
    ) throws URISyntaxException {
        log.debug("REST request to update Categorycombo : {}, {}", id, categorycombo);
        if (categorycombo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorycombo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorycomboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        categorycombo = categorycomboService.update(categorycombo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorycombo.getId()))
            .body(categorycombo);
    }

    /**
     * {@code PATCH  /categorycombos/:id} : Partial updates given fields of an existing categorycombo, field will ignore if it is null
     *
     * @param id the id of the categorycombo to save.
     * @param categorycombo the categorycombo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorycombo,
     * or with status {@code 400 (Bad Request)} if the categorycombo is not valid,
     * or with status {@code 404 (Not Found)} if the categorycombo is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorycombo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Categorycombo> partialUpdateCategorycombo(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Categorycombo categorycombo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Categorycombo partially : {}, {}", id, categorycombo);
        if (categorycombo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorycombo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorycomboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Categorycombo> result = categorycomboService.partialUpdate(categorycombo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorycombo.getId())
        );
    }

    /**
     * {@code GET  /categorycombos} : get all the categorycombos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorycombos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Categorycombo>> getAllCategorycombos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Categorycombos");
        Page<Categorycombo> page = categorycomboService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorycombos/:id} : get the "id" categorycombo.
     *
     * @param id the id of the categorycombo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorycombo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Categorycombo> getCategorycombo(@PathVariable("id") String id) {
        log.debug("REST request to get Categorycombo : {}", id);
        Optional<Categorycombo> categorycombo = categorycomboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorycombo);
    }

    /**
     * {@code DELETE  /categorycombos/:id} : delete the "id" categorycombo.
     *
     * @param id the id of the categorycombo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorycombo(@PathVariable("id") String id) {
        log.debug("REST request to delete Categorycombo : {}", id);
        categorycomboService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
