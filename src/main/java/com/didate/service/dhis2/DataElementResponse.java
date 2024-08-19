package com.didate.service.dhis2;

import com.didate.domain.Dataelement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataElementResponse {

    @JsonProperty("pager")
    private Pager pager;

    @JsonProperty("dataElements")
    private List<Dataelement> dataElements;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Dataelement> getDataElements() {
        return dataElements;
    }

    public void setDataElements(List<Dataelement> dataElements) {
        this.dataElements = dataElements;
    }

    @Override
    public String toString() {
        return "DataElementResponse [dataElements=" + dataElements + "]";
    }
}
