package com.didate.service.dhis2.response;

import com.didate.domain.Indicator;
import com.didate.service.dhis2.Pager;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class IndicatorResponse {

    @JsonProperty("pager")
    private Pager pager;

    @JsonProperty("indicators")
    private List<Indicator> indicators;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }
}
