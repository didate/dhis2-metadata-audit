package com.didate.service.dto;

import com.didate.domain.Indicator;
import com.didate.domain.IndicatorType;

public class IndicatorDTO extends AbstractDTO {

    private IndicatorType indicatorType;
    private Integer revisionNumber;

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
            indicator.getShortName(),
            indicator.getCreated().toString(),
            indicator.getLastUpdated().toString(),
            indicator.getCreatedBy(),
            indicator.getLastUpdatedBy()
        );
        this.indicatorType = indicator.getIndicatorType();
    }

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public IndicatorDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
