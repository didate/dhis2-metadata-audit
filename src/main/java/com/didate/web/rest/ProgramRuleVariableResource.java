package com.didate.web.rest;

import com.didate.domain.ProgramRuleVariable;
import com.didate.service.ProgramRuleVariableService;
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
    public ResponseEntity<List<ProgramRuleVariable>> getAllProgramRuleVariables(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProgramRuleVariables");
        Page<ProgramRuleVariable> page = programRuleVariableService.findAll(pageable);
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
}
