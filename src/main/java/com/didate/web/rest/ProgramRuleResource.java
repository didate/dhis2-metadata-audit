package com.didate.web.rest;

import com.didate.domain.ProgramRule;
import com.didate.service.ProgramRuleService;
import com.didate.service.dto.ProgramRuleDTO;
import com.didate.service.dto.ProgramRuleFullDTO;
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
 * REST controller for managing {@link com.didate.domain.ProgramRule}.
 */
@RestController
@RequestMapping("/api")
public class ProgramRuleResource {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleResource.class);

    private final ProgramRuleService programRuleService;

    public ProgramRuleResource(ProgramRuleService programRuleService) {
        this.programRuleService = programRuleService;
    }

    /**
     * {@code GET  /program-rules} : get all the programRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programRules in body.
     */
    @GetMapping("/program-rules")
    public ResponseEntity<List<ProgramRuleDTO>> getAllProgramRules(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of ProgramRules");
        Page<ProgramRuleDTO> page = programRuleService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-rules/:id} : get the "id" programRule.
     *
     * @param id the id of the programRule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programRule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-rules/{id}")
    public ResponseEntity<ProgramRule> getProgramRule(@PathVariable String id) {
        log.debug("REST request to get ProgramRule : {}", id);
        Optional<ProgramRule> programRule = programRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programRule);
    }

    @GetMapping("/program-rules/{id}/audit")
    @Timed
    public ResponseEntity<List<ProgramRuleDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(programRuleService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/program-rules/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<ProgramRuleFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        ProgramRuleFullDTO latestRevisionDTO = programRuleService.findAuditRevision(id, Math.max(rev1, rev2));
        ProgramRuleFullDTO earlierRevisionDTO = programRuleService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
