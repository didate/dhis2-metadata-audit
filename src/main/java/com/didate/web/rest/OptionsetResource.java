package com.didate.web.rest;

import com.didate.domain.OptionSet;
import com.didate.service.OptionsetService;
import com.didate.service.dto.OptionSetDTO;
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
 * REST controller for managing {@link com.didate.domain.OptionSet}.
 */
@RestController
@RequestMapping("/api")
public class OptionsetResource {

    private final Logger log = LoggerFactory.getLogger(OptionsetResource.class);

    private final OptionsetService optionsetService;

    public OptionsetResource(OptionsetService optionsetService) {
        this.optionsetService = optionsetService;
    }

    /**
     * {@code GET  /optionsets} : get all the optionsets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionsets in body.
     */
    @GetMapping("/optionsets")
    public ResponseEntity<List<OptionSetDTO>> getAllOptionsets(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of Optionsets");
        Page<OptionSetDTO> page = optionsetService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /optionsets/:id} : get the "id" optionset.
     *
     * @param id the id of the optionset to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionset, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/optionsets/{id}")
    public ResponseEntity<OptionSet> getOptionset(@PathVariable String id) {
        log.debug("REST request to get OptionSet : {}", id);
        Optional<OptionSet> optionset = optionsetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionset);
    }

    @GetMapping("/optionsets/{id}/audit")
    @Timed
    public ResponseEntity<List<OptionSetDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(optionsetService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/optionsets/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<OptionSetDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        OptionSetDTO latestRevisionDTO = optionsetService.findAuditRevision(id, Math.max(rev1, rev2));
        OptionSetDTO earlierRevisionDTO = optionsetService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
