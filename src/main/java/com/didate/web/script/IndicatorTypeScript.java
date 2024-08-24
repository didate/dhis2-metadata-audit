package com.didate.web.script;

import com.didate.domain.IndicatorType;
import com.didate.domain.Project;
import com.didate.service.IndicatortypeService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IndicatorTypeScript {

    private static final Logger log = LoggerFactory.getLogger(IndicatorTypeScript.class);

    private final DhisApiService<IndicatorType> indicatorTypeApiService;
    private final IndicatortypeService indicatorTypeService;

    public IndicatorTypeScript(DhisApiService<IndicatorType> indicatorTypeApiService, IndicatortypeService indicatorTypeService) {
        this.indicatorTypeApiService = indicatorTypeApiService;
        this.indicatorTypeService = indicatorTypeService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling indicatorTypes API...");

        List<IndicatorType> indicatorTypes = indicatorTypeApiService.getData(
            project,
            "indicatorTypes",
            new TypeReference<Dhis2ApiResponse<IndicatorType>>() {}
        );

        for (IndicatorType indicatorType : indicatorTypes) {
            if (!indicatorTypeService.exist(indicatorType.getId())) {
                indicatorTypeService.save(indicatorType);
            }
        }

        log.info("Fetched indicator types: {}", indicatorTypes.size());
    }
}
