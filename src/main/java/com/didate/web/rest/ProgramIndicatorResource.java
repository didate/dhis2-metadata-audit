package com.didate.web.rest;

import com.didate.domain.ProgramIndicator;
import com.didate.service.ProgramIndicatorService;
import com.didate.service.dto.ProgramIndicatorDTO;
import com.didate.service.dto.ProgramIndicatorFullDTO;
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
 * REST controller for managing {@link com.didate.domain.ProgramIndicator}.
 */
@RestController
@RequestMapping("/api")
public class ProgramIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(ProgramIndicatorResource.class);

    private final ProgramIndicatorService programIndicatorService;

    public ProgramIndicatorResource(ProgramIndicatorService programIndicatorService) {
        this.programIndicatorService = programIndicatorService;
    }

    /**
     * {@code GET  /program-indicators} : get all the programIndicators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programIndicators in body.
     */
    @GetMapping("/program-indicators")
    public ResponseEntity<List<ProgramIndicatorDTO>> getAllProgramIndicators(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of ProgramIndicators");
        Page<ProgramIndicatorDTO> page = programIndicatorService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-indicators/:id} : get the "id" programIndicator.
     *
     * @param id the id of the programIndicator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programIndicator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-indicators/{id}")
    public ResponseEntity<ProgramIndicator> getProgramIndicator(@PathVariable String id) {
        log.debug("REST request to get ProgramIndicator : {}", id);
        Optional<ProgramIndicator> programIndicator = programIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programIndicator);
    }

    @GetMapping("/program-indicators/{id}/audit")
    @Timed
    public ResponseEntity<List<ProgramIndicatorDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(programIndicatorService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/program-indicators/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<ProgramIndicatorFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        ProgramIndicatorFullDTO latestRevisionDTO = programIndicatorService.findAuditRevision(id, Math.max(rev1, rev2));
        ProgramIndicatorFullDTO earlierRevisionDTO = programIndicatorService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
