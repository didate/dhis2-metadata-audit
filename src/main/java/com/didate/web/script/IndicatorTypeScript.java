package com.didate.web.script;

import com.didate.domain.IndicatorType;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.IndicatortypeService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IndicatorTypeScript {

    private static final Logger log = LoggerFactory.getLogger(IndicatorTypeScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<IndicatorType> indicatorTypeApiService;
    private final IndicatortypeService indicatorTypeService;

    public IndicatorTypeScript(DhisApiService<IndicatorType> indicatorTypeApiService, IndicatortypeService indicatorTypeService) {
        this.indicatorTypeApiService = indicatorTypeApiService;
        this.indicatorTypeService = indicatorTypeService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling indicator types API...");

        boolean hasExistingTypes = indicatorTypeService.count() > 0;
        String lastUpdated = hasExistingTypes ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<IndicatorType> indicatorTypes = indicatorTypeApiService.getData(
            project,
            "indicatorTypes",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<IndicatorType>>() {}
        );

        for (IndicatorType type : indicatorTypes) {
            TypeTrack typeTrack = determineTypeTrack(type.getId(), hasExistingTypes);
            type = type.shortName(type.getName()).createdBy(type.getLastUpdatedBy()).track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                indicatorTypeService.partialUpdate(type);
            } else {
                indicatorTypeService.save(type);
            }
        }

        log.info("Fetched indicator types: {}", indicatorTypes.size());
    }

    private TypeTrack determineTypeTrack(String typeId, boolean hasExistingTypes) {
        if (!hasExistingTypes) {
            return TypeTrack.NONE;
        }
        return indicatorTypeService.exist(typeId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
