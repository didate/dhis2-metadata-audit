package com.didate.web.script;

import com.didate.domain.OrganisationUnit;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.OrganisationUnitService;
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
public class OrganisationUnitScript {

    private static final Logger log = LoggerFactory.getLogger(OrganisationUnitScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        log.info("Calling organisation units API...");

        boolean hasExistingUnits = organisationUnitService.count() > 0;
        String lastUpdated = hasExistingUnits ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<OrganisationUnit> organisationUnits = organisationUnitApiService.getData(
            project,
            "organisationUnits",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<OrganisationUnit>>() {}
        );

        for (OrganisationUnit unit : organisationUnits) {
            TypeTrack typeTrack = determineTypeTrack(unit.getId(), hasExistingUnits);
            unit = unit.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                organisationUnitService.partialUpdate(unit);
            } else {
                organisationUnitService.save(unit);
            }
        }

        log.info("Fetched organisation units: {}", organisationUnits.size());
    }

    private TypeTrack determineTypeTrack(String unitId, boolean hasExistingUnits) {
        if (!hasExistingUnits) {
            return TypeTrack.NONE;
        }
        return organisationUnitService.exist(unitId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
