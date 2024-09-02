package com.didate.service.dto;

import com.didate.domain.DataElement;

public class DataElementDTO extends AbstractDTO {

    private String domainType;
    private Integer revisionNumber;

    public DataElementDTO(DataElement dataElement) {
        super(
            dataElement.getId(),
            dataElement.getName(),
            dataElement.getShortName(),
            dataElement.getCreated(),
            dataElement.getLastUpdated(),
            dataElement.getCreatedBy(),
            dataElement.getLastUpdatedBy()
        );
        this.domainType = dataElement.getDomainType();
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public DataElementDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
