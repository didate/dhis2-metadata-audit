package com.didate.service.dhis2.response;

import com.didate.domain.Dataelement;
import com.didate.domain.Dataset;
import com.didate.service.dhis2.Pager;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DataSetResponse {

    @JsonProperty("pager")
    private Pager pager;

    @JsonProperty("dataSets")
    private List<Dataset> dataSets;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Dataset> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<Dataset> dataSets) {
        this.dataSets = dataSets;
    }
}
