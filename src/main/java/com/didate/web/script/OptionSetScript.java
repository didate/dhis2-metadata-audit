package com.didate.web.script;

import com.didate.domain.OptionSet;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.OptionsetService;
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
public class OptionSetScript {

    private static final Logger log = LoggerFactory.getLogger(OptionSetScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<OptionSet> optionSetApiService;
    private final OptionsetService optionSetService;

    public OptionSetScript(DhisApiService<OptionSet> optionSetApiService, OptionsetService optionSetService) {
        this.optionSetApiService = optionSetApiService;
        this.optionSetService = optionSetService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling option sets API...");

        boolean hasExistingSets = optionSetService.count() > 0;
        String lastUpdated = hasExistingSets ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<OptionSet> optionSets = optionSetApiService.getData(
            project,
            "optionSets",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<OptionSet>>() {}
        );

        for (OptionSet set : optionSets) {
            TypeTrack typeTrack = determineTypeTrack(set.getId(), hasExistingSets);
            set = set.shortName(set.getName()).track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                optionSetService.partialUpdate(set);
            } else {
                optionSetService.save(set);
            }
        }

        log.info("Fetched option sets: {}", optionSets.size());
    }

    private TypeTrack determineTypeTrack(String setId, boolean hasExistingSets) {
        if (!hasExistingSets) {
            return TypeTrack.NONE;
        }
        return optionSetService.exist(setId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
