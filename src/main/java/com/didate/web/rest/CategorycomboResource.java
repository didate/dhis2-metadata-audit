package com.didate.web.rest;

import com.didate.domain.CategoryCombo;
import com.didate.service.CategorycomboService;
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
    public ResponseEntity<List<CategoryCombo>> getAllCategorycombos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Categorycombos");
        Page<CategoryCombo> page = categorycomboService.findAll(pageable);
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
}
