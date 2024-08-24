package com.didate.web.script;

import com.didate.domain.OrganisationUnit;
import com.didate.domain.Project;
import com.didate.service.OrganisationUnitService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrganisationUnitScript {

    private static final Logger log = LoggerFactory.getLogger(OrganisationUnitScript.class);

    private final DhisApiService<OrganisationUnit> organisationUnitApiService;
    private final OrganisationUnitService organisationUnitService;

    public OrganisationUnitScript(
        DhisApiService<OrganisationUnit> organisationUnitApiService,
        OrganisationUnitService organisationUnitService
    ) {
        this.organisationUnitApiService = organisationUnitApiService;
        this.organisationUnitService = organisationUnitService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling organisationUnits API...");

        List<OrganisationUnit> organisationUnits = organisationUnitApiService.getData(
            project,
            "organisationUnits",
            new TypeReference<Dhis2ApiResponse<OrganisationUnit>>() {}
        );

        for (OrganisationUnit organisationUnit : organisationUnits) {
            if (!organisationUnitService.exist(organisationUnit.getId())) {
                organisationUnitService.save(organisationUnit);
            }
        }

        log.info("Fetched organisation units: {}", organisationUnits.size());
    }
}
