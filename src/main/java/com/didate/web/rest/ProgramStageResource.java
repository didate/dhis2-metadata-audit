package com.didate.web.rest;

import com.didate.domain.ProgramStage;
import com.didate.repository.ProgramStageRepository;
import com.didate.service.ProgramStageService;
import com.didate.service.dto.ProgramStageDTO;
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
 * REST controller for managing {@link com.didate.domain.ProgramStage}.
 */
@RestController
@RequestMapping("/api")
public class ProgramStageResource {

    private final Logger log = LoggerFactory.getLogger(ProgramStageResource.class);

    private final ProgramStageService programStageService;

    public ProgramStageResource(ProgramStageService programStageService) {
        this.programStageService = programStageService;
    }

    /**
     * {@code GET  /program-stages} : get all the programStages.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programStages in body.
     */
    @GetMapping("/program-stages")
    public ResponseEntity<List<ProgramStageDTO>> getAllProgramStages(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ProgramStages");
        Page<ProgramStageDTO> page = programStageService.findAllProgramStage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-stages/:id} : get the "id" programStage.
     *
     * @param id the id of the programStage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programStage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-stages/{id}")
    public ResponseEntity<ProgramStage> getProgramStage(@PathVariable String id) {
        log.debug("REST request to get ProgramStage : {}", id);
        Optional<ProgramStage> programStage = programStageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programStage);
    }
}
