package com.didate.service.dto;

import com.didate.domain.Indicator;
import com.didate.domain.IndicatorType;

public class IndicatorDTO extends AbstractDTO {

    private IndicatorType indicatorType;

    public IndicatorType getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    public IndicatorDTO(Indicator indicator) {
        super(
            indicator.getId(),
            indicator.getName(),
            indicator.getCreated().toString(),
            indicator.getLastUpdated().toString(),
            indicator.getCreatedBy(),
            indicator.getLastUpdatedBy()
        );
        this.indicatorType = indicator.getIndicatorType();
    }
}
