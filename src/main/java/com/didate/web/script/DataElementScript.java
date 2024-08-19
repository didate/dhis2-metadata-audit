package com.didate.web.script;

import com.didate.domain.Dataelement;
import com.didate.service.CategorycomboService;
import com.didate.service.DHISUserService;
import com.didate.service.DataelementService;
import com.didate.service.OptionsetService;
import com.didate.service.dhis2.DataElementResponse;
import com.didate.service.dhis2.Dhis2Service;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataElementScript {

    private static final Logger log = LoggerFactory.getLogger(DataElementScript.class);
    private final Dhis2Service dhis2Service;
    private final DataelementService dataelementService;
    private final DHISUserService dhisUserService;
    private final OptionsetService optionsetService;
    private final CategorycomboService categorycomboService;

    public DataElementScript(
        Dhis2Service dhis2Service,
        DataelementService dataelementService,
        DHISUserService dhisUserService,
        OptionsetService optionsetService,
        CategorycomboService categorycomboService
    ) {
        this.dhis2Service = dhis2Service;
        this.dataelementService = dataelementService;
        this.categorycomboService = categorycomboService;
        this.optionsetService = optionsetService;
        this.dhisUserService = dhisUserService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void get() throws IOException {
        log.info("Calling dataElements API...");
        List<Dataelement> dataelements = dhis2Service.getDataElements();

        dataelements
            .stream()
            .filter(e -> !dataelementService.exist(e.getId()))
            .forEach(e -> {
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getCreatedBy().getId()))) {
                    dhisUserService.save(e.getCreatedBy());
                }
                if (Boolean.FALSE.equals(dhisUserService.exist(e.getLastUpdatedBy().getId()))) {
                    dhisUserService.save(e.getLastUpdatedBy());
                }

                if (e.getOptionSet() != null && Boolean.FALSE.equals(optionsetService.exist(e.getOptionSet().getId()))) {
                    optionsetService.save(e.getOptionSet());
                }
                if (e.getCategoryCombo() != null && Boolean.FALSE.equals(categorycomboService.exist(e.getCategoryCombo().getId()))) {
                    categorycomboService.save(e.getCategoryCombo());
                }

                dataelementService.save(e);
            });

        log.info("Fetched data elements: {}", dataelements.size());
    }
}
