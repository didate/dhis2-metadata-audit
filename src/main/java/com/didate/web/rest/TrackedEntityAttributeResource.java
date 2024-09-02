package com.didate.web.rest;

import com.didate.domain.TrackedEntityAttribute;
import com.didate.service.TrackedEntityAttributeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<TrackedEntityAttribute>> getAllTrackedEntityAttributes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TrackedEntityAttributes");
        Page<TrackedEntityAttribute> page = trackedEntityAttributeService.findAll(pageable);
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
}
