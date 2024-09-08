package com.didate.web.rest;

import com.didate.domain.OrganisationUnit;
import com.didate.service.OrganisationUnitService;
import com.didate.service.dto.OrganisationUnitDTO;
import com.didate.service.dto.OrganisationUnitFullDTO;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<List<OrganisationUnitDTO>> getAllOrganisationUnits(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of OrganisationUnits");
        Page<OrganisationUnitDTO> page = organisationUnitService.findAll(pageable, id, name);
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

    @GetMapping("/organisation-units/{id}/audit")
    @Timed
    public ResponseEntity<List<OrganisationUnitDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(organisationUnitService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/organisation-units/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<OrganisationUnitFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        OrganisationUnitFullDTO latestRevisionDTO = organisationUnitService.findAuditRevision(id, Math.max(rev1, rev2));
        OrganisationUnitFullDTO earlierRevisionDTO = organisationUnitService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
