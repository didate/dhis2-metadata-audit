package com.didate.web.rest;

import com.didate.domain.Indicator;
import com.didate.service.IndicatorService;
import com.didate.service.dto.IndicatorDTO;
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
 * REST controller for managing {@link com.didate.domain.Indicator}.
 */
@RestController
@RequestMapping("/api")
public class IndicatorResource {

    private final Logger log = LoggerFactory.getLogger(IndicatorResource.class);

    private final IndicatorService indicatorService;

    public IndicatorResource(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }

    /**
     * {@code GET  /indicators} : get all the indicators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicators in body.
     */
    @GetMapping("/indicators")
    public ResponseEntity<List<IndicatorDTO>> getAllIndicators(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Indicators");
        Page<IndicatorDTO> page = indicatorService.findAllIndicators(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /indicators/:id} : get the "id" indicator.
     *
     * @param id the id of the indicator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indicators/{id}")
    public ResponseEntity<Indicator> getIndicator(@PathVariable String id) {
        log.debug("REST request to get Indicator : {}", id);
        Optional<Indicator> indicator = indicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicator);
    }
}
