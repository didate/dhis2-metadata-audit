package com.didate.service.dhis2.response;

import com.didate.domain.DataElement;
import com.didate.service.dhis2.Pager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataElementResponse {

    @JsonProperty("pager")
    private Pager pager;

    @JsonProperty("dataElements")
    private List<DataElement> dataElements;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<DataElement> getDataElements() {
        return dataElements;
    }

    public void setDataElements(List<DataElement> dataElements) {
        this.dataElements = dataElements;
    }

    @Override
    public String toString() {
        return "DataElementResponse [dataElements=" + dataElements + "]";
    }
}
