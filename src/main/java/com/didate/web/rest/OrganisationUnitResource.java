package com.didate.web.rest;

import com.didate.domain.OrganisationUnit;
import com.didate.repository.OrganisationUnitRepository;
import com.didate.service.OrganisationUnitService;
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
 * REST controller for managing {@link com.didate.domain.OrganisationUnit}.
 */
@RestController
@RequestMapping("/api")
public class OrganisationUnitResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationUnitResource.class);

    private final OrganisationUnitService organisationUnitService;

    public OrganisationUnitResource(OrganisationUnitService organisationUnitService) {
        this.organisationUnitService = organisationUnitService;
    }

    /**
     * {@code GET  /organisation-units} : get all the organisationUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisationUnits in body.
     */
    @GetMapping("/organisation-units")
    public ResponseEntity<List<OrganisationUnit>> getAllOrganisationUnits(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OrganisationUnits");
        Page<OrganisationUnit> page = organisationUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisation-units/:id} : get the "id" organisationUnit.
     *
     * @param id the id of the organisationUnit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisationUnit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisation-units/{id}")
    public ResponseEntity<OrganisationUnit> getOrganisationUnit(@PathVariable String id) {
        log.debug("REST request to get OrganisationUnit : {}", id);
        Optional<OrganisationUnit> organisationUnit = organisationUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisationUnit);
    }
}
