package com.didate.service.dto;

import com.didate.domain.DataSet;

public class DataSetDTO extends AbstractDTO {

    private String periodType;
    private Integer revisionNumber;

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

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public DataSetDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
