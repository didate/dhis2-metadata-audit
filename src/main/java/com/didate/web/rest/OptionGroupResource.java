package com.didate.web.rest;

import com.didate.domain.OptionGroup;
import com.didate.service.OptionGroupService;
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
 * REST controller for managing {@link com.didate.domain.OptionGroup}.
 */
@RestController
@RequestMapping("/api")
public class OptionGroupResource {

    private final Logger log = LoggerFactory.getLogger(OptionGroupResource.class);

    private final OptionGroupService optionGroupService;

    public OptionGroupResource(OptionGroupService optionGroupService) {
        this.optionGroupService = optionGroupService;
    }

    /**
     * {@code GET  /option-groups} : get all the optionGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionGroups in body.
     */
    @GetMapping("/option-groups")
    public ResponseEntity<List<OptionGroup>> getAllOptionGroups(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OptionGroups");
        Page<OptionGroup> page = optionGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /option-groups/:id} : get the "id" optionGroup.
     *
     * @param id the id of the optionGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option-groups/{id}")
    public ResponseEntity<OptionGroup> getOptionGroup(@PathVariable String id) {
        log.debug("REST request to get OptionGroup : {}", id);
        Optional<OptionGroup> optionGroup = optionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionGroup);
    }
}
