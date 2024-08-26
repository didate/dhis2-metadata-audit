package com.didate.web.script;

import com.didate.domain.OptionGroup;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.OptionGroupService;
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
public class OptionGroupScript {

    private static final Logger log = LoggerFactory.getLogger(OptionGroupScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<OptionGroup> optionGroupApiService;
    private final OptionGroupService optionGroupService;

    public OptionGroupScript(DhisApiService<OptionGroup> optionGroupApiService, OptionGroupService optionGroupService) {
        this.optionGroupApiService = optionGroupApiService;
        this.optionGroupService = optionGroupService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling option groups API...");

        boolean hasExistingOptionGroups = optionGroupService.count() > 0;
        String lastUpdated = hasExistingOptionGroups ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<OptionGroup> optionGroups = optionGroupApiService.getData(
            project,
            "optionGroups",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<OptionGroup>>() {}
        );

        for (OptionGroup optionGroup : optionGroups) {
            TypeTrack typeTrack = determineTypeTrack(optionGroup.getId(), hasExistingOptionGroups);
            optionGroup = optionGroup.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                optionGroupService.partialUpdate(optionGroup);
            } else {
                optionGroupService.save(optionGroup);
            }
        }

        log.info("Fetched option groups: {}", optionGroups.size());
    }

    private TypeTrack determineTypeTrack(String optionGroupId, boolean hasExistingOptionGroups) {
        if (!hasExistingOptionGroups) {
            return TypeTrack.NONE;
        }
        return optionGroupService.exist(optionGroupId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
