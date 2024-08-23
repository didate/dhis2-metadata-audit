package com.didate.web.rest;

import com.didate.domain.OptionGroup;
import com.didate.repository.OptionGroupRepository;
import com.didate.service.OptionGroupService;
import com.didate.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
 * REST controller for managing {@link com.didate.domain.OptionGroup}.
 */
@RestController
@RequestMapping("/api/option-groups")
public class OptionGroupResource {

    private static final Logger log = LoggerFactory.getLogger(OptionGroupResource.class);

    private static final String ENTITY_NAME = "optionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionGroupService optionGroupService;

    private final OptionGroupRepository optionGroupRepository;

    public OptionGroupResource(OptionGroupService optionGroupService, OptionGroupRepository optionGroupRepository) {
        this.optionGroupService = optionGroupService;
        this.optionGroupRepository = optionGroupRepository;
    }

    /**
     * {@code POST  /option-groups} : Create a new optionGroup.
     *
     * @param optionGroup the optionGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionGroup, or with status {@code 400 (Bad Request)} if the optionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OptionGroup> createOptionGroup(@Valid @RequestBody OptionGroup optionGroup) throws URISyntaxException {
        log.debug("REST request to save OptionGroup : {}", optionGroup);
        if (optionGroup.getId() != null) {
            throw new BadRequestAlertException("A new optionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        optionGroup = optionGroupService.save(optionGroup);
        return ResponseEntity.created(new URI("/api/option-groups/" + optionGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, optionGroup.getId()))
            .body(optionGroup);
    }

    /**
     * {@code GET  /option-groups} : get all the optionGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OptionGroup>> getAllOptionGroups(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OptionGroups");
        Page<OptionGroup> page = optionGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /option-groups/:id} : get the "id" optionGroup.
     *
     * @param id the id of the optionGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OptionGroup> getOptionGroup(@PathVariable("id") String id) {
        log.debug("REST request to get OptionGroup : {}", id);
        Optional<OptionGroup> optionGroup = optionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionGroup);
    }

    /**
     * {@code DELETE  /option-groups/:id} : delete the "id" optionGroup.
     *
     * @param id the id of the optionGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOptionGroup(@PathVariable("id") String id) {
        log.debug("REST request to delete OptionGroup : {}", id);
        optionGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
