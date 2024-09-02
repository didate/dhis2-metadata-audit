package com.didate.service.dto;

import com.didate.domain.OrganisationUnit;
import java.time.Instant;

public class OrganisationUnitFullDTO extends OrganisationUnitDTO {

    private String path;
    private Instant openingDate;
    private Integer level;

    public OrganisationUnitFullDTO(OrganisationUnit organisationUnit) {
        super(organisationUnit);
        this.path = organisationUnit.getPath();
        this.openingDate = organisationUnit.getOpeningDate();
        this.level = organisationUnit.getLevel();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Instant openingDate) {
        this.openingDate = openingDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
