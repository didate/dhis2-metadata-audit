package com.didate.web.rest;

import com.didate.domain.IndicatorType;
import com.didate.service.IndicatortypeService;
import com.didate.service.dto.IndicatorTypeDTO;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.didate.domain.IndicatorType}.
 */
@RestController
@RequestMapping("/api")
public class IndicatortypeResource {

    private final Logger log = LoggerFactory.getLogger(IndicatortypeResource.class);

    private final IndicatortypeService indicatortypeService;

    public IndicatortypeResource(IndicatortypeService indicatortypeService) {
        this.indicatortypeService = indicatortypeService;
    }

    /**
     * {@code GET  /indicatortypes} : get all the indicatortypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicatortypes in body.
     */
    @GetMapping("/indicatortypes")
    public ResponseEntity<List<IndicatorType>> getAllIndicatortypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Indicatortypes");
        Page<IndicatorType> page = indicatortypeService.findAll(pageable);
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
    public ResponseEntity<IndicatorType> getIndicatortype(@PathVariable String id) {
        log.debug("REST request to get Indicatortype : {}", id);
        Optional<IndicatorType> indicatortype = indicatortypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicatortype);
    }

    @GetMapping("/indicatortypes/{id}/audit")
    @Timed
    public ResponseEntity<List<IndicatorTypeDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(indicatortypeService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/indicatortypes/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<IndicatorTypeDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        IndicatorTypeDTO latestRevisionDTO = indicatortypeService.findAuditRevision(id, Math.max(rev1, rev2));
        IndicatorTypeDTO earlierRevisionDTO = indicatortypeService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
