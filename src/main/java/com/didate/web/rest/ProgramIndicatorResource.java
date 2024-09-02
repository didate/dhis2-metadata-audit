package com.didate.web.rest;

import com.didate.domain.ProgramIndicator;
import com.didate.service.ProgramIndicatorService;
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
    public ResponseEntity<List<ProgramIndicator>> getAllProgramIndicators(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProgramIndicators");
        Page<ProgramIndicator> page = programIndicatorService.findAll(pageable);
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
}
