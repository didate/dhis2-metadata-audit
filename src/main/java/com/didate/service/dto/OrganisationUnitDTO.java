package com.didate.service.dto;

import com.didate.domain.OrganisationUnit;

public class OrganisationUnitDTO extends AbstractDTO {

    private Integer revisionNumber;
    private Integer level;

    public OrganisationUnitDTO(OrganisationUnit organisationUnit) {
        super(
            organisationUnit.getId(),
            organisationUnit.getName(),
            organisationUnit.getCreated(),
            organisationUnit.getLastUpdated(),
            organisationUnit.getCreatedBy(),
            organisationUnit.getLastUpdatedBy()
        );
        this.level = organisationUnit.getLevel();
    }

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
