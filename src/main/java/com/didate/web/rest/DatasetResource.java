package com.didate.web.rest;

import com.didate.domain.DataSet;
import com.didate.service.DatasetService;
import com.didate.service.dto.DataSetDTO;
import com.didate.service.dto.DataSetFullDTO;
import com.didate.web.rest.util.RemoveCommonWords;
import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
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
 * REST controller for managing {@link com.didate.domain.DataSet}.
 */
@RestController
@RequestMapping("/api")
public class DatasetResource {

    private final Logger log = LoggerFactory.getLogger(DatasetResource.class);

    private final DatasetService datasetService;

    public DatasetResource(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    /**
     * {@code GET  /datasets} : get all the datasets.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of datasets in body.
     */
    @GetMapping("/datasets")
    public ResponseEntity<List<DataSetDTO>> getAllDatasetDataSetDTOs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        log.debug("REST request to get a page of Datasets");
        Page<DataSetDTO> page = datasetService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /datasets/:id} : get the "id" dataset.
     *
     * @param id the id of the dataset to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the dataset, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/datasets/{id}")
    public ResponseEntity<DataSet> getDataset(@PathVariable String id) {
        log.debug("REST request to get DataSet : {}", id);
        Optional<DataSet> dataset = datasetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataset);
    }

    @GetMapping("/datasets/{id}/audit")
    @Timed
    public ResponseEntity<List<DataSetDTO>> findRevisions(@PathVariable String id) {
        return new ResponseEntity<>(datasetService.findAudits(id), HttpStatus.OK);
    }

    @GetMapping("/datasets/{id}/compare/{rev1}/{rev2}")
    @Timed
    public ResponseEntity<List<DataSetFullDTO>> findRevisions(
        @PathVariable String id,
        @PathVariable Integer rev1,
        @PathVariable Integer rev2
    ) {
        // Retrieve both revisions, with the latest revision first
        DataSetFullDTO latestRevisionDTO = datasetService.findAuditRevision(id, Math.max(rev1, rev2));
        DataSetFullDTO earlierRevisionDTO = datasetService.findAuditRevision(id, Math.min(rev1, rev2));

        // Check if either revision is not found (Optional)
        if (latestRevisionDTO == null || earlierRevisionDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Compute the differences and update DTOs
        String[] orgUnitDiff = RemoveCommonWords.remove(
            latestRevisionDTO.getOrganisationUnitsContent(),
            earlierRevisionDTO.getOrganisationUnitsContent()
        );
        latestRevisionDTO.setOrganisationUnitsContent(orgUnitDiff[0]);
        earlierRevisionDTO.setOrganisationUnitsContent(orgUnitDiff[1]);

        String[] dataElementsDiff = RemoveCommonWords.remove(
            latestRevisionDTO.getDataSetElementsContent(),
            earlierRevisionDTO.getDataSetElementsContent()
        );
        latestRevisionDTO.setDataSetElementsContent(dataElementsDiff[0]);
        earlierRevisionDTO.setDataSetElementsContent(dataElementsDiff[1]);

        String[] indicatorsDiff = RemoveCommonWords.remove(
            latestRevisionDTO.getIndicatorsContent(),
            earlierRevisionDTO.getIndicatorsContent()
        );
        latestRevisionDTO.setIndicatorsContent(indicatorsDiff[0]);
        earlierRevisionDTO.setIndicatorsContent(indicatorsDiff[1]);

        // Return the two compared revisions in a response
        return new ResponseEntity<>(List.of(latestRevisionDTO, earlierRevisionDTO), HttpStatus.OK);
    }
}
