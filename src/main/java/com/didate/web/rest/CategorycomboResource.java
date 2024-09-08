package com.didate.web.rest;

import com.didate.domain.CategoryCombo;
import com.didate.service.CategorycomboService;
import com.didate.service.dto.CategoryComboDTO;
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
 * REST controller for managing {@link com.didate.domain.CategoryCombo}.
 */
@RestController
@RequestMapping("/api")
public class CategorycomboResource {

    private final Logger log = LoggerFactory.getLogger(CategorycomboResource.class);

    private final CategorycomboService categorycomboService;

    public CategorycomboResource(CategorycomboService categorycomboService) {
        this.categorycomboService = categorycomboService;
    }

    /**
     * {@code GET  /categorycombos} : get all the categorycombos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorycombos in body.
     */
    @GetMapping("/categorycombos")
    public ResponseEntity<List<CategoryComboDTO>> getAllCategorycombos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of Categorycombos");
        Page<CategoryComboDTO> page = categorycomboService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorycombos/:id} : get the "id" categorycombo.
     *
     * @param id the id of the categorycombo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorycombo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorycombos/{id}")
    public ResponseEntity<CategoryCombo> getCategorycombo(@PathVariable String id) {
        log.debug("REST request to get Categorycombo : {}", id);
        Optional<CategoryCombo> categorycombo = categorycomboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorycombo);
    }

    @GetMapping("/categorycombos/{id}/audit")
    @Timed
    public ResponseEntity<List<CategoryComboDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(categorycomboService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/categorycombos/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<CategoryComboDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        CategoryComboDTO latestRevisionDTO = categorycomboService.findAuditRevision(id, Math.max(rev1, rev2));
        CategoryComboDTO earlierRevisionDTO = categorycomboService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
