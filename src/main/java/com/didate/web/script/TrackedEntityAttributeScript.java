package com.didate.web.script;

import com.didate.domain.Project;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.TrackedEntityAttributeService;
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
public class TrackedEntityAttributeScript {

    private static final Logger log = LoggerFactory.getLogger(TrackedEntityAttributeScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<TrackedEntityAttribute> trackedEntityAttributeApiService;
    private final TrackedEntityAttributeService trackedEntityAttributeService;

    public TrackedEntityAttributeScript(
        DhisApiService<TrackedEntityAttribute> trackedEntityAttributeApiService,
        TrackedEntityAttributeService trackedEntityAttributeService
    ) {
        this.trackedEntityAttributeApiService = trackedEntityAttributeApiService;
        this.trackedEntityAttributeService = trackedEntityAttributeService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling tracked entity attributes API...");

        boolean hasExistingAttributes = trackedEntityAttributeService.count() > 0;
        String lastUpdated = hasExistingAttributes ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<TrackedEntityAttribute> trackedEntityAttributes = trackedEntityAttributeApiService.getData(
            project,
            "trackedEntityAttributes",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<TrackedEntityAttribute>>() {}
        );

        for (TrackedEntityAttribute attribute : trackedEntityAttributes) {
            TypeTrack typeTrack = determineTypeTrack(attribute.getId(), hasExistingAttributes);
            attribute = attribute.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                trackedEntityAttributeService.partialUpdate(attribute);
            } else {
                trackedEntityAttributeService.save(attribute);
            }
            System.out.println("=====================================================");
            System.out.println(attribute.getCreatedBy());
            System.out.println("=====================================================");
            System.out.println(attribute.getLastUpdatedBy());
            System.out.println("=====================================================");
        }

        log.info("Fetched tracked entity attributes: {}", trackedEntityAttributes.size());
    }

    private TypeTrack determineTypeTrack(String attributeId, boolean hasExistingAttributes) {
        if (!hasExistingAttributes) {
            return TypeTrack.NONE;
        }
        return trackedEntityAttributeService.exist(attributeId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
