package com.didate.web.script;

import com.didate.domain.DataElement;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.CategorycomboService;
import com.didate.service.DHISUserService;
import com.didate.service.DataelementService;
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
public class DataElementScript {

    private static final Logger log = LoggerFactory.getLogger(DataElementScript.class);

    private final DhisApiService<DataElement> dataElementApiService;
    private final DataelementService dataelementService;
    private final DHISUserService dhisUserService;
    private final OptionsetService optionsetService;
    private final CategorycomboService categorycomboService;

    public DataElementScript(
        DhisApiService<DataElement> dataElementApiService,
        DataelementService dataelementService,
        DHISUserService dhisUserService,
        OptionsetService optionsetService,
        CategorycomboService categorycomboService
    ) {
        this.dataElementApiService = dataElementApiService;
        this.dataelementService = dataelementService;
        this.dhisUserService = dhisUserService;
        this.optionsetService = optionsetService;
        this.categorycomboService = categorycomboService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling dataElements API...");

        List<DataElement> dataelements = dataElementApiService.getData(
            project,
            "dataElements",
            new TypeReference<Dhis2ApiResponse<DataElement>>() {}
        );

        for (DataElement element : dataelements) {
            if (!dataelementService.exist(element.getId())) {
                if (!dhisUserService.exist(element.getCreatedBy().getId())) {
                    dhisUserService.save(element.getCreatedBy().track(TypeTrack.NONE));
                }
                if (!dhisUserService.exist(element.getLastUpdatedBy().getId())) {
                    dhisUserService.save(element.getLastUpdatedBy().track(TypeTrack.NONE));
                }

                if (element.getOptionSet() != null && !optionsetService.exist(element.getOptionSet().getId())) {
                    optionsetService.save(element.getOptionSet());
                }
                if (element.getCategoryCombo() != null && !categorycomboService.exist(element.getCategoryCombo().getId())) {
                    categorycomboService.save(element.getCategoryCombo());
                }

                dataelementService.save(element.track(TypeTrack.NONE));
            }
        }

        log.info("Fetched data elements: {}", dataelements.size());
    }
}
