package com.didate.web.script;

import com.didate.domain.DataSet;
import com.didate.domain.Project;
import com.didate.service.DHISUserService;
import com.didate.service.DatasetService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataSetScript {

    private static final Logger log = LoggerFactory.getLogger(DataSetScript.class);

    private final DhisApiService<DataSet> datasetApiService;
    private final DatasetService datasetService;
    private final DHISUserService dhisUserService;

    public DataSetScript(DhisApiService<DataSet> datasetApiService, DatasetService datasetService, DHISUserService dhisUserService) {
        this.datasetApiService = datasetApiService;
        this.datasetService = datasetService;
        this.dhisUserService = dhisUserService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling datasets API...");

        List<DataSet> datasets = datasetApiService.getData(project, "datasets", new TypeReference<Dhis2ApiResponse<DataSet>>() {});

        for (DataSet dataset : datasets) {
            if (!datasetService.exist(dataset.getId())) {
                if (!dhisUserService.exist(dataset.getCreatedBy().getId())) {
                    dhisUserService.save(dataset.getCreatedBy());
                }
                if (!dhisUserService.exist(dataset.getLastUpdatedBy().getId())) {
                    dhisUserService.save(dataset.getLastUpdatedBy());
                }

                datasetService.save(dataset);
            }
        }

        log.info("Fetched datasets: {}", datasets.size());
    }
}
