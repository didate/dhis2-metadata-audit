package com.didate.service.dhis2.response;

import com.didate.domain.DataElement;
import com.didate.domain.DataSet;
import com.didate.service.dhis2.Pager;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DataSetResponse {

    @JsonProperty("pager")
    private Pager pager;

    @JsonProperty("dataSets")
    private List<DataSet> dataSets;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<DataSet> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }
}
