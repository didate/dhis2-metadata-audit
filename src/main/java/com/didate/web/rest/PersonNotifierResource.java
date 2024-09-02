package com.didate.web.rest;

import com.didate.domain.PersonNotifier;
import com.didate.repository.PersonNotifierRepository;
import com.didate.service.PersonNotifierService;
import com.didate.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
 * REST controller for managing {@link com.didate.domain.PersonNotifier}.
 */
@RestController
@RequestMapping("/api")
public class PersonNotifierResource {

    private final Logger log = LoggerFactory.getLogger(PersonNotifierResource.class);

    private static final String ENTITY_NAME = "personNotifier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonNotifierService personNotifierService;

    private final PersonNotifierRepository personNotifierRepository;

    public PersonNotifierResource(PersonNotifierService personNotifierService, PersonNotifierRepository personNotifierRepository) {
        this.personNotifierService = personNotifierService;
        this.personNotifierRepository = personNotifierRepository;
    }

    /**
     * {@code POST  /person-notifiers} : Create a new personNotifier.
     *
     * @param personNotifier the personNotifier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personNotifier, or with status {@code 400 (Bad Request)} if the personNotifier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-notifiers")
    public ResponseEntity<PersonNotifier> createPersonNotifier(@Valid @RequestBody PersonNotifier personNotifier)
        throws URISyntaxException {
        log.debug("REST request to save PersonNotifier : {}", personNotifier);
        if (personNotifier.getId() != null) {
            throw new BadRequestAlertException("A new personNotifier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonNotifier result = personNotifierService.save(personNotifier);
        return ResponseEntity
            .created(new URI("/api/person-notifiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-notifiers/:id} : Updates an existing personNotifier.
     *
     * @param id the id of the personNotifier to save.
     * @param personNotifier the personNotifier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personNotifier,
     * or with status {@code 400 (Bad Request)} if the personNotifier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personNotifier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-notifiers/{id}")
    public ResponseEntity<PersonNotifier> updatePersonNotifier(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonNotifier personNotifier
    ) throws URISyntaxException {
        log.debug("REST request to update PersonNotifier : {}, {}", id, personNotifier);
        if (personNotifier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personNotifier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personNotifierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonNotifier result = personNotifierService.update(personNotifier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personNotifier.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-notifiers/:id} : Partial updates given fields of an existing personNotifier, field will ignore if it is null
     *
     * @param id the id of the personNotifier to save.
     * @param personNotifier the personNotifier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personNotifier,
     * or with status {@code 400 (Bad Request)} if the personNotifier is not valid,
     * or with status {@code 404 (Not Found)} if the personNotifier is not found,
     * or with status {@code 500 (Internal Server Error)} if the personNotifier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-notifiers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonNotifier> partialUpdatePersonNotifier(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonNotifier personNotifier
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonNotifier partially : {}, {}", id, personNotifier);
        if (personNotifier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personNotifier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personNotifierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonNotifier> result = personNotifierService.partialUpdate(personNotifier);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personNotifier.getId().toString())
        );
    }

    /**
     * {@code GET  /person-notifiers} : get all the personNotifiers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personNotifiers in body.
     */
    @GetMapping("/person-notifiers")
    public ResponseEntity<List<PersonNotifier>> getAllPersonNotifiers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PersonNotifiers");
        Page<PersonNotifier> page = personNotifierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-notifiers/:id} : get the "id" personNotifier.
     *
     * @param id the id of the personNotifier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personNotifier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-notifiers/{id}")
    public ResponseEntity<PersonNotifier> getPersonNotifier(@PathVariable Long id) {
        log.debug("REST request to get PersonNotifier : {}", id);
        Optional<PersonNotifier> personNotifier = personNotifierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personNotifier);
    }

    /**
     * {@code DELETE  /person-notifiers/:id} : delete the "id" personNotifier.
     *
     * @param id the id of the personNotifier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-notifiers/{id}")
    public ResponseEntity<Void> deletePersonNotifier(@PathVariable Long id) {
        log.debug("REST request to delete PersonNotifier : {}", id);
        personNotifierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
