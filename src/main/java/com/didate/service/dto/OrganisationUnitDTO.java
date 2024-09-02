package com.didate.service.dto;

import com.didate.domain.OrganisationUnit;

public class OrganisationUnitDTO extends AbstractDTO {

    public OrganisationUnitDTO(OrganisationUnit organisationUnit) {
        super(
            organisationUnit.getId(),
            organisationUnit.getName(),
            organisationUnit.getCreated().toString(),
            organisationUnit.getLastUpdated().toString(),
            organisationUnit.getCreatedBy(),
            organisationUnit.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public OrganisationUnitDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
