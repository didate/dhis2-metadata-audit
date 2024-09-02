package com.didate.web.rest;

import com.didate.domain.OptionSet;
import com.didate.repository.OptionsetRepository;
import com.didate.service.OptionsetService;
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
 * REST controller for managing {@link com.didate.domain.OptionSet}.
 */
@RestController
@RequestMapping("/api")
public class OptionsetResource {

    private final Logger log = LoggerFactory.getLogger(OptionsetResource.class);

    private final OptionsetService optionsetService;

    public OptionsetResource(OptionsetService optionsetService) {
        this.optionsetService = optionsetService;
    }

    /**
     * {@code GET  /optionsets} : get all the optionsets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionsets in body.
     */
    @GetMapping("/optionsets")
    public ResponseEntity<List<OptionSet>> getAllOptionsets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Optionsets");
        Page<OptionSet> page = optionsetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /optionsets/:id} : get the "id" optionset.
     *
     * @param id the id of the optionset to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionset, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/optionsets/{id}")
    public ResponseEntity<OptionSet> getOptionset(@PathVariable String id) {
        log.debug("REST request to get OptionSet : {}", id);
        Optional<OptionSet> optionset = optionsetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionset);
    }
}
