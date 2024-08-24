package com.didate.web.script;

import com.didate.domain.OptionGroup;
import com.didate.domain.Project;
import com.didate.service.OptionGroupService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OptionGroupScript {

    private static final Logger log = LoggerFactory.getLogger(OptionGroupScript.class);

    private final DhisApiService<OptionGroup> optionGroupApiService;
    private final OptionGroupService optionGroupService;

    public OptionGroupScript(DhisApiService<OptionGroup> optionGroupApiService, OptionGroupService optionGroupService) {
        this.optionGroupApiService = optionGroupApiService;
        this.optionGroupService = optionGroupService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling optionGroups API...");

        List<OptionGroup> optionGroups = optionGroupApiService.getData(
            project,
            "optionGroups",
            new TypeReference<Dhis2ApiResponse<OptionGroup>>() {}
        );

        for (OptionGroup optionGroup : optionGroups) {
            if (!optionGroupService.exist(optionGroup.getId())) {
                optionGroupService.save(optionGroup);
            }
        }

        log.info("Fetched option groups: {}", optionGroups.size());
    }
}
