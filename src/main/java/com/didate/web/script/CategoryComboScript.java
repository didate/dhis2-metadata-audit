package com.didate.web.script;

import com.didate.domain.CategoryCombo;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.service.CategorycomboService;
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
public class CategoryComboScript {

    private static final Logger log = LoggerFactory.getLogger(CategoryComboScript.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DhisApiService<CategoryCombo> categoryComboApiService;
    private final CategorycomboService categoryComboService;

    public CategoryComboScript(DhisApiService<CategoryCombo> categoryComboApiService, CategorycomboService categoryComboService) {
        this.categoryComboApiService = categoryComboApiService;
        this.categoryComboService = categoryComboService;
    }

    public void perform(Project project) throws IOException {
        log.info("Calling category combos API...");

        boolean hasExistingCombos = categoryComboService.count() > 0;
        String lastUpdated = hasExistingCombos ? LocalDate.now().format(DATE_FORMATTER) : "";

        List<CategoryCombo> categoryCombos = categoryComboApiService.getData(
            project,
            "categoryCombos",
            lastUpdated,
            new TypeReference<Dhis2ApiResponse<CategoryCombo>>() {}
        );

        for (CategoryCombo combo : categoryCombos) {
            TypeTrack typeTrack = determineTypeTrack(combo.getId(), hasExistingCombos);
            combo = combo.shortName(combo.getName()).track(typeTrack).project(project);
            if (typeTrack == TypeTrack.UPDATE) {
                categoryComboService.partialUpdate(combo);
            } else {
                categoryComboService.save(combo);
            }
        }

        log.info("Fetched category combos: {}", categoryCombos.size());
    }

    private TypeTrack determineTypeTrack(String comboId, boolean hasExistingCombos) {
        if (!hasExistingCombos) {
            return TypeTrack.NONE;
        }
        return categoryComboService.exist(comboId) ? TypeTrack.UPDATE : TypeTrack.NEW;
    }
}
