package com.didate.web.rest;

import com.didate.domain.DHISUser;
import com.didate.service.DHISUserService;
import com.didate.service.dto.DHISUserDTO;
import com.didate.service.dto.DHISUserFullDTO;
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
    public ResponseEntity<List<DHISUserDTO>> getAllDHISUsers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) Integer months
    ) {
        log.debug("REST request to get a page of DHISUsers");
        Page<DHISUserDTO> page = dHISUserService.findAll(pageable, id, name, username, months);
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

    @GetMapping("/dhis-users/{id}/audit")
    @Timed
    public ResponseEntity<List<DHISUserDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(dHISUserService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/dhis-users/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<DHISUserFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        DHISUserFullDTO latestRevisionDTO = dHISUserService.findAuditRevision(id, Math.max(rev1, rev2));
        DHISUserFullDTO earlierRevisionDTO = dHISUserService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
