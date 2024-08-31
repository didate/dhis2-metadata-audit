package com.didate.web.script;

import com.didate.domain.DataElement;
import com.didate.domain.DataSet;
import com.didate.domain.Indicator;
import com.didate.domain.OrganisationUnit;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.DataelementService;
import com.didate.service.DatasetService;
import com.didate.service.IndicatorService;
import com.didate.service.OrganisationUnitService;
import com.didate.service.dhis2.DhisApiService;
import com.didate.service.dhis2.response.Dhis2ApiResponse;
import com.didate.web.rest.util.RemoveCommonWords;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataSetScript {

    private static final Logger log = LoggerFactory.getLogger(DataSetScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<DataSet> dataSetApiService;
    private final DatasetService dataSetService;
    private final DataelementService dataelementService;
    private final IndicatorService indicatorService;
    private final OrganisationUnitService organisationUnitService;

    public DataSetScript(
        DhisApiService<DataSet> dataSetApiService,
        DatasetService dataSetService,
        DataelementService dataelementService,
        IndicatorService indicatorService,
        OrganisationUnitService organisationUnitService
    ) {
        this.dataSetApiService = dataSetApiService;
        this.dataSetService = dataSetService;
        this.dataelementService = dataelementService;
        this.indicatorService = indicatorService;
        this.organisationUnitService = organisationUnitService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling data sets API...");

        boolean hasExistingSets = dataSetService.count() > 0;
        String lastUpdated = hasExistingSets ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<DataSet> dataSets = dataSetApiService.getData(
            project,
            "dataSets",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<DataSet>>() {}
        );

        for (DataSet dataSet : dataSets) {
            dataSet
                .organisationUnitsCount(dataSet.getOrganisationUnits().size())
                .dataSetElementsCount(dataSet.getDataSetElements().size())
                .indicatorsCount(dataSet.getIndicators().size());

            dataSet.setDataSetElementsContent(
                dataSet.getDataSetElements().stream().map(ds -> ds.getId()).collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            dataSet.setOrganisationUnitsContent(
                dataSet.getOrganisationUnits().stream().map(ds -> ds.getId()).collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            dataSet.setIndicatorsContent(
                dataSet.getIndicators().stream().map(ds -> ds.getId()).collect(Collectors.joining(RemoveCommonWords.SPLITER))
            );

            Set<DataElement> dataElementSet = new HashSet<>();
            for (DataElement dataElement : dataSet.getDataSetElements()) {
                dataElementSet.add(dataelementService.findOne(dataElement.getId()).get());
            }
            dataSet.setDataSetElements(dataElementSet);

            Set<Indicator> indicatorSet = new HashSet<>();
            for (Indicator indicator : dataSet.getIndicators()) {
                indicatorSet.add(indicatorService.findOne(indicator.getId()).get());
            }
            dataSet.setIndicators(indicatorSet);

            Set<OrganisationUnit> organisationUnitSet = new HashSet<>();
            for (OrganisationUnit organisationUnit : dataSet.getOrganisationUnits()) {
                organisationUnitSet.add(organisationUnitService.findOne(organisationUnit.getId()).get());
            }
            dataSet.setOrganisationUnits(organisationUnitSet);

            TypeTrack typeTrack = determineTypeTrack(dataSet.getId(), hasExistingSets);
            dataSet = dataSet.track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                dataSetService.partialUpdate(dataSet);
            } else {
                dataSetService.save(dataSet);
            }
        }

        log.info("Fetched data sets: {}", dataSets.size());
    }

    private TypeTrack determineTypeTrack(String dataSetId, boolean hasExistingSets) {
        if (!hasExistingSets) {
            return TypeTrack.NONE;
        }
        return dataSetService.exist(dataSetId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
