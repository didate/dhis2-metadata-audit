package com.didate.web.script;

import com.didate.domain.DataElement;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.DataelementService;
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
public class DataElementScript {

    private static final Logger log = LoggerFactory.getLogger(DataElementScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<DataElement> dataElementApiService;
    private final DataelementService dataElementService;

    public DataElementScript(DhisApiService<DataElement> dataElementApiService, DataelementService dataElementService) {
        this.dataElementApiService = dataElementApiService;
        this.dataElementService = dataElementService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling data elements API...");

        boolean hasExistingElements = dataElementService.count() > 0;
        String lastUpdated = hasExistingElements ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<DataElement> dataElements = dataElementApiService.getData(
            project,
            "dataElements",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<DataElement>>() {}
        );

        for (DataElement element : dataElements) {
            TypeTrack typeTrack = determineTypeTrack(element.getId(), hasExistingElements);
            element = element.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                dataElementService.partialUpdate(element);
            } else {
                dataElementService.save(element);
            }
        }

        log.info("Fetched data elements: {}", dataElements.size());
    }

    private TypeTrack determineTypeTrack(String elementId, boolean hasExistingElements) {
        if (!hasExistingElements) {
            return TypeTrack.NONE;
        }
        return dataElementService.exist(elementId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
