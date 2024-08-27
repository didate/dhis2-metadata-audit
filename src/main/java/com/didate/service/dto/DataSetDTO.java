package com.didate.service.dto;

import com.didate.domain.DataSet;

public class DataSetDTO extends AbstractDTO {

    private String periodType;

    public DataSetDTO(DataSet dataSet) {
        super(
            dataSet.getId(),
            dataSet.getName(),
            dataSet.getCreated().toString(),
            dataSet.getLastUpdated().toString(),
            dataSet.getCreatedBy(),
            dataSet.getLastUpdatedBy()
        );
        this.periodType = dataSet.getPeriodType();
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
