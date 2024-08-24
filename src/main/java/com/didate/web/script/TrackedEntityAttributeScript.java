package com.didate.web.script;

import com.didate.domain.Project;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.service.TrackedEntityAttributeService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrackedEntityAttributeScript {

    private static final Logger log = LoggerFactory.getLogger(TrackedEntityAttributeScript.class);

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
        log.info("Calling trackedEntityAttributes API...");

        List<TrackedEntityAttribute> attributes = trackedEntityAttributeApiService.getData(
            project,
            "trackedEntityAttributes",
            new TypeReference<Dhis2ApiResponse<TrackedEntityAttribute>>() {}
        );

        for (TrackedEntityAttribute attribute : attributes) {
            if (!trackedEntityAttributeService.exist(attribute.getId())) {
                trackedEntityAttributeService.save(attribute);
            }
        }

        log.info("Fetched tracked entity attributes: {}", attributes.size());
    }
}
