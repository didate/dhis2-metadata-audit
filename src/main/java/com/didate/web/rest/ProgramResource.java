package com.didate.web.rest;

import com.didate.domain.Program;
import com.didate.service.ProgramService;
import com.didate.service.dto.ProgramDTO;
import com.didate.service.dto.ProgramFullDTO;
import com.didate.web.rest.util.RemoveCommonWords;
import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.didate.domain.Program}.
 */
@RestController
@RequestMapping("/api")
public class ProgramResource {

    private final Logger log = LoggerFactory.getLogger(ProgramResource.class);

    private static final String ENTITY_NAME = "program";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramService programService;

    public ProgramResource(ProgramService programService) {
        this.programService = programService;
    }

    /**
     * {@code GET  /programs} : get all the programs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programs in body.
     */
    @GetMapping("/programs")
    public ResponseEntity<List<ProgramDTO>> getAllPrograms(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Programs");
        Page<ProgramDTO> page = programService.findAllPrograms(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /programs/:id} : get the "id" program.
     *
     * @param id the id of the program to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the program, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programs/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable String id) {
        log.debug("REST request to get Program : {}", id);
        Optional<Program> program = programService.findOne(id);
        return ResponseUtil.wrapOrNotFound(program);
    }

    @GetMapping("/programs/{id}/audit")
    @Timed
    public ResponseEntity<List<ProgramDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(programService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/programs/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<ProgramFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        List<ProgramFullDTO> programFullDTOs = new ArrayList<>();
        ProgramFullDTO programFullDTO1 = programService.findAuditRevision(id, Math.max(rev1, rev2));
        ProgramFullDTO programFullDTO2 = programService.findAuditRevision(id, Math.min(rev1, rev2));

        String[] orgUnit = RemoveCommonWords.remove(
            programFullDTO1.getOrganisationUnitsContent(),
            programFullDTO2.getOrganisationUnitsContent()
        );
        String[] programAttribute = RemoveCommonWords.remove(
            programFullDTO1.getProgramTrackedEntityAttributesContent(),
            programFullDTO2.getProgramTrackedEntityAttributesContent()
        );
        String[] programStage = RemoveCommonWords.remove(
            programFullDTO1.getProgramStagesContent(),
            programFullDTO2.getProgramStagesContent()
        );
        String[] programInidicator = RemoveCommonWords.remove(
            programFullDTO1.getProgramIndicatorsContent(),
            programFullDTO2.getProgramIndicatorsContent()
        );

        programFullDTO1.setOrganisationUnitsContent(orgUnit[0]);
        programFullDTO2.setOrganisationUnitsContent(orgUnit[1]);
        programFullDTO1.setProgramTrackedEntityAttributesContent(programAttribute[0]);
        programFullDTO2.setProgramTrackedEntityAttributesContent(programAttribute[1]);

        programFullDTO1.setProgramStagesContent(programStage[0]);
        programFullDTO2.setProgramStagesContent(programStage[1]);

        programFullDTO1.setProgramIndicatorsContent(programInidicator[0]);
        programFullDTO2.setProgramIndicatorsContent(programInidicator[1]);

        programFullDTOs.add(programFullDTO1);
        programFullDTOs.add(programFullDTO2);

        return new ResponseEntity<>(programFullDTOs, HttpStatus.OK);
    }
}
