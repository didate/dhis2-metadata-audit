package com.didate.web.rest;

import com.didate.domain.DataElement;
import com.didate.service.DataelementService;
import com.didate.service.dto.DataElementDTO;
import com.didate.service.dto.DataElementDTO;
import com.didate.service.dto.DataElementFullDTO;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.didate.domain.DataElement}.
 */
@RestController
@RequestMapping("/api")
public class DataelementResource {

    private final Logger log = LoggerFactory.getLogger(DataelementResource.class);

    private final DataelementService dataelementService;

    public DataelementResource(DataelementService dataelementService) {
        this.dataelementService = dataelementService;
    }

    /**
     * {@code GET  /dataelements} : get all the dataelements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataelements in body.
     */
    @GetMapping("/dataelements")
    public ResponseEntity<List<DataElementDTO>> getAllDataelements(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Dataelements");
        Page<DataElementDTO> page = dataelementService.findAllDataElements(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dataelements/:id} : get the "id" dataelement.
     *
     * @param id the id of the dataelement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataelement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dataelements/{id}")
    public ResponseEntity<DataElement> getDataelement(@PathVariable String id) {
        log.debug("REST request to get Dataelement : {}", id);
        Optional<DataElement> dataelement = dataelementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataelement);
    }

    @GetMapping("/dataelements/{id}/audit")
    @Timed
    public ResponseEntity<List<DataElementDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(dataelementService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/dataelements/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<DataElementFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        DataElementFullDTO latestRevisionDTO = dataelementService.findAuditRevision(id, Math.max(rev1, rev2));
        DataElementFullDTO earlierRevisionDTO = dataelementService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
