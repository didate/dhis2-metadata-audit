package com.didate.service.dto;

import com.didate.domain.DataElement;

public class DataElementDTO extends AbstractDTO {

    private String domainType;

    public DataElementDTO(DataElement dataElement) {
        super(
            dataElement.getId(),
            dataElement.getName(),
            dataElement.getCreated().toString(),
            dataElement.getLastUpdated().toString(),
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
}
