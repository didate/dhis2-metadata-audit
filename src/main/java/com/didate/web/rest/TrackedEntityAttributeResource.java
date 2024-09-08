package com.didate.web.rest;

import com.didate.domain.TrackedEntityAttribute;
import com.didate.service.TrackedEntityAttributeService;
import com.didate.service.dto.ProgramStageDTO;
import com.didate.service.dto.TrackedEntityAttributeDTO;
import com.didate.service.dto.TrackedEntityAttributeFullDTO;
import com.didate.web.rest.util.RemoveCommonWords;
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
 * REST controller for managing {@link com.didate.domain.TrackedEntityAttribute}.
 */
@RestController
@RequestMapping("/api")
public class TrackedEntityAttributeResource {

    private final Logger log = LoggerFactory.getLogger(TrackedEntityAttributeResource.class);

    private final TrackedEntityAttributeService trackedEntityAttributeService;

    public TrackedEntityAttributeResource(TrackedEntityAttributeService trackedEntityAttributeService) {
        this.trackedEntityAttributeService = trackedEntityAttributeService;
    }

    /**
     * {@code GET  /tracked-entity-attributes} : get all the trackedEntityAttributes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trackedEntityAttributes in body.
     */
    @GetMapping("/tracked-entity-attributes")
    public ResponseEntity<List<TrackedEntityAttributeDTO>> getAllTrackedEntityAttributes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of TrackedEntityAttributes");
        Page<TrackedEntityAttributeDTO> page = trackedEntityAttributeService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tracked-entity-attributes/:id} : get the "id" trackedEntityAttribute.
     *
     * @param id the id of the trackedEntityAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trackedEntityAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tracked-entity-attributes/{id}")
    public ResponseEntity<TrackedEntityAttribute> getTrackedEntityAttribute(@PathVariable String id) {
        log.debug("REST request to get TrackedEntityAttribute : {}", id);
        Optional<TrackedEntityAttribute> trackedEntityAttribute = trackedEntityAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trackedEntityAttribute);
    }

    @GetMapping("/tracked-entity-attributes/{id}/audit")
    @Timed
    public ResponseEntity<List<TrackedEntityAttributeDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(trackedEntityAttributeService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/tracked-entity-attributes/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<TrackedEntityAttributeFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        TrackedEntityAttributeFullDTO latestRevisionDTO = trackedEntityAttributeService.findAuditRevision(id, Math.max(rev1, rev2));
        TrackedEntityAttributeFullDTO earlierRevisionDTO = trackedEntityAttributeService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
