package com.didate.web.rest;

import com.didate.domain.ProgramRule;
import com.didate.service.ProgramRuleService;
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
    public ResponseEntity<List<ProgramRule>> getAllProgramRules(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProgramRules");
        Page<ProgramRule> page = programRuleService.findAll(pageable);
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
}
