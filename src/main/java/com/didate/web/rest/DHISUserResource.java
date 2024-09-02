package com.didate.web.rest;

import com.didate.domain.DHISUser;
import com.didate.repository.DHISUserRepository;
import com.didate.service.DHISUserService;
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
 * REST controller for managing {@link com.didate.domain.DHISUser}.
 */
@RestController
@RequestMapping("/api")
public class DHISUserResource {

    private final Logger log = LoggerFactory.getLogger(DHISUserResource.class);

    private final DHISUserService dHISUserService;

    public DHISUserResource(DHISUserService dHISUserService) {
        this.dHISUserService = dHISUserService;
    }

    /**
     * {@code GET  /dhis-users} : get all the dHISUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dHISUsers in body.
     */
    @GetMapping("/dhis-users")
    public ResponseEntity<List<DHISUser>> getAllDHISUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DHISUsers");
        Page<DHISUser> page = dHISUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dhis-users/:id} : get the "id" dHISUser.
     *
     * @param id the id of the dHISUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dHISUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dhis-users/{id}")
    public ResponseEntity<DHISUser> getDHISUser(@PathVariable String id) {
        log.debug("REST request to get DHISUser : {}", id);
        Optional<DHISUser> dHISUser = dHISUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dHISUser);
    }
}
