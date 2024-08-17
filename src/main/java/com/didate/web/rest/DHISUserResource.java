package com.didate.web.rest;

import com.didate.domain.DHISUser;
import com.didate.repository.DHISUserRepository;
import com.didate.service.DHISUserService;
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
 * REST controller for managing {@link com.didate.domain.DHISUser}.
 */
@RestController
@RequestMapping("/api/dhis-users")
public class DHISUserResource {

    private static final Logger log = LoggerFactory.getLogger(DHISUserResource.class);

    private static final String ENTITY_NAME = "dHISUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DHISUserService dHISUserService;

    private final DHISUserRepository dHISUserRepository;

    public DHISUserResource(DHISUserService dHISUserService, DHISUserRepository dHISUserRepository) {
        this.dHISUserService = dHISUserService;
        this.dHISUserRepository = dHISUserRepository;
    }

    /**
     * {@code POST  /dhis-users} : Create a new dHISUser.
     *
     * @param dHISUser the dHISUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dHISUser, or with status {@code 400 (Bad Request)} if the dHISUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DHISUser> createDHISUser(@Valid @RequestBody DHISUser dHISUser) throws URISyntaxException {
        log.debug("REST request to save DHISUser : {}", dHISUser);
        if (dHISUser.getId() != null) {
            throw new BadRequestAlertException("A new dHISUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dHISUser = dHISUserService.save(dHISUser);
        return ResponseEntity.created(new URI("/api/dhis-users/" + dHISUser.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dHISUser.getId()))
            .body(dHISUser);
    }

    /**
     * {@code PUT  /dhis-users/:id} : Updates an existing dHISUser.
     *
     * @param id the id of the dHISUser to save.
     * @param dHISUser the dHISUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dHISUser,
     * or with status {@code 400 (Bad Request)} if the dHISUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dHISUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DHISUser> updateDHISUser(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DHISUser dHISUser
    ) throws URISyntaxException {
        log.debug("REST request to update DHISUser : {}, {}", id, dHISUser);
        if (dHISUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dHISUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dHISUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dHISUser = dHISUserService.update(dHISUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dHISUser.getId()))
            .body(dHISUser);
    }

    /**
     * {@code PATCH  /dhis-users/:id} : Partial updates given fields of an existing dHISUser, field will ignore if it is null
     *
     * @param id the id of the dHISUser to save.
     * @param dHISUser the dHISUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dHISUser,
     * or with status {@code 400 (Bad Request)} if the dHISUser is not valid,
     * or with status {@code 404 (Not Found)} if the dHISUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the dHISUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DHISUser> partialUpdateDHISUser(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DHISUser dHISUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update DHISUser partially : {}, {}", id, dHISUser);
        if (dHISUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dHISUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dHISUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DHISUser> result = dHISUserService.partialUpdate(dHISUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dHISUser.getId())
        );
    }

    /**
     * {@code GET  /dhis-users} : get all the dHISUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dHISUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DHISUser>> getAllDHISUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DHISUsers");
        Page<DHISUser> page = dHISUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dhis-users/:id} : get the "id" dHISUser.
     *
     * @param id the id of the dHISUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dHISUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DHISUser> getDHISUser(@PathVariable("id") String id) {
        log.debug("REST request to get DHISUser : {}", id);
        Optional<DHISUser> dHISUser = dHISUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dHISUser);
    }

    /**
     * {@code DELETE  /dhis-users/:id} : delete the "id" dHISUser.
     *
     * @param id the id of the dHISUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDHISUser(@PathVariable("id") String id) {
        log.debug("REST request to delete DHISUser : {}", id);
        dHISUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
