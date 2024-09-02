package com.didate.web.rest;

import com.didate.domain.ProgramRuleAction;
import com.didate.repository.ProgramRuleActionRepository;
import com.didate.service.ProgramRuleActionService;
import com.didate.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.didate.domain.ProgramRuleAction}.
 */
@RestController
@RequestMapping("/api")
public class ProgramRuleActionResource {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleActionResource.class);

    private final ProgramRuleActionService programRuleActionService;

    public ProgramRuleActionResource(ProgramRuleActionService programRuleActionService) {
        this.programRuleActionService = programRuleActionService;
    }

    /**
     * {@code GET  /program-rule-actions} : get all the programRuleActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programRuleActions in body.
     */
    @GetMapping("/program-rule-actions")
    public ResponseEntity<List<ProgramRuleAction>> getAllProgramRuleActions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProgramRuleActions");
        Page<ProgramRuleAction> page = programRuleActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-rule-actions/:id} : get the "id" programRuleAction.
     *
     * @param id the id of the programRuleAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programRuleAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-rule-actions/{id}")
    public ResponseEntity<ProgramRuleAction> getProgramRuleAction(@PathVariable String id) {
        log.debug("REST request to get ProgramRuleAction : {}", id);
        Optional<ProgramRuleAction> programRuleAction = programRuleActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programRuleAction);
    }
}
