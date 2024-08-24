package com.didate.web.script;

import com.didate.domain.OptionSet;
import com.didate.domain.Project;
import com.didate.service.OptionsetService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OptionSetScript {

    private static final Logger log = LoggerFactory.getLogger(OptionSetScript.class);

    private final DhisApiService<OptionSet> optionSetApiService;
    private final OptionsetService optionSetService;

    public OptionSetScript(DhisApiService<OptionSet> optionSetApiService, OptionsetService optionSetService) {
        this.optionSetApiService = optionSetApiService;
        this.optionSetService = optionSetService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling optionSets API...");

        List<OptionSet> optionSets = optionSetApiService.getData(
            project,
            "optionSets",
            new TypeReference<Dhis2ApiResponse<OptionSet>>() {}
        );

        for (OptionSet optionSet : optionSets) {
            if (!optionSetService.exist(optionSet.getId())) {
                optionSetService.save(optionSet);
            }
        }

        log.info("Fetched option sets: {}", optionSets.size());
    }
}
