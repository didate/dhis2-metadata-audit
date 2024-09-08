package com.didate.web.rest;

import com.didate.domain.ProgramRuleVariable;
import com.didate.service.ProgramRuleVariableService;
import com.didate.service.dto.ProgramRuleVariableDTO;
import com.didate.service.dto.ProgramRuleVariableFullDTO;
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
 * REST controller for managing {@link com.didate.domain.ProgramRuleVariable}.
 */
@RestController
@RequestMapping("/api")
public class ProgramRuleVariableResource {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleVariableResource.class);

    private final ProgramRuleVariableService programRuleVariableService;

    public ProgramRuleVariableResource(ProgramRuleVariableService programRuleVariableService) {
        this.programRuleVariableService = programRuleVariableService;
    }

    /**
     * {@code GET  /program-rule-variables} : get all the programRuleVariables.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programRuleVariables in body.
     */
    @GetMapping("/program-rule-variables")
    public ResponseEntity<List<ProgramRuleVariableDTO>> getAllProgramRuleVariables(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of ProgramRuleVariables");
        Page<ProgramRuleVariableDTO> page = programRuleVariableService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-rule-variables/:id} : get the "id" programRuleVariable.
     *
     * @param id the id of the programRuleVariable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programRuleVariable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-rule-variables/{id}")
    public ResponseEntity<ProgramRuleVariable> getProgramRuleVariable(@PathVariable String id) {
        log.debug("REST request to get ProgramRuleVariable : {}", id);
        Optional<ProgramRuleVariable> programRuleVariable = programRuleVariableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programRuleVariable);
    }

    @GetMapping("/program-rule-variables/{id}/audit")
    @Timed
    public ResponseEntity<List<ProgramRuleVariableDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(programRuleVariableService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/program-rule-variables/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<ProgramRuleVariableFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        ProgramRuleVariableFullDTO latestRevisionDTO = programRuleVariableService.findAuditRevision(id, Math.max(rev1, rev2));
        ProgramRuleVariableFullDTO earlierRevisionDTO = programRuleVariableService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
