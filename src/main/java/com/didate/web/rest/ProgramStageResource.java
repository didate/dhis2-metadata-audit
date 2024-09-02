package com.didate.web.rest;

import com.didate.domain.ProgramStage;
import com.didate.service.ProgramStageService;
import com.didate.service.dto.ProgramStageDTO;
import com.didate.service.dto.ProgramStageFullDTO;
import com.didate.web.rest.util.RemoveCommonWords;
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

    @GetMapping("/program-stages/{id}/audit")
    @Timed
    public ResponseEntity<List<ProgramStageDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(programStageService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/program-stages/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<ProgramStageFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        ProgramStageFullDTO latestRevisionDTO = programStageService.findAuditRevision(id, Math.max(rev1, rev2));
        ProgramStageFullDTO earlierRevisionDTO = programStageService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Compute the differences and update DTOs

        String[] dataElementsDiff = RemoveCommonWords.remove(
            latestRevisionDTO.getProgramStageDataElementsContent(),
            earlierRevisionDTO.getProgramStageDataElementsContent()
        );
        latestRevisionDTO.setProgramStageDataElementsContent(dataElementsDiff[0]);
        earlierRevisionDTO.setProgramStageDataElementsContent(dataElementsDiff[1]);

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
