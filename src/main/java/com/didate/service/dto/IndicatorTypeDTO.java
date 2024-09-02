package com.didate.service.dto;

import com.didate.domain.IndicatorType;

public class IndicatorTypeDTO extends AbstractDTO {

    public IndicatorTypeDTO(IndicatorType indicatorType) {
        super(
            indicatorType.getId(),
            indicatorType.getName(),
            indicatorType.getShortName(),
            indicatorType.getCreated(),
            indicatorType.getLastUpdated(),
            indicatorType.getCreatedBy(),
            indicatorType.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public IndicatorTypeDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
