package com.didate.service.dhis2.response;

import com.didate.service.dhis2.Pager;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dhis2ApiResponse<T> {

    @JsonProperty("pager")
    private Pager pager;

    @JsonAlias(
        {
            "dataElements",
            "indicators",
            "programs",
            "dataSets",
            "optionGroups",
            "trackedEntityAttributes",
            "programStages",
            "programIndicators",
            "programRules",
            "programRuleVariables",
            "programRuleActions",
            "organisationUnits",
            "categoryCombos",
            "optionSets",
            "indicatorTypes",
            "users",
        }
    )
    private List<T> items;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
