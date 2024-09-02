package com.didate.service.dto;

import com.didate.domain.DHISUser;

public class AbstractDTO {

    private String id;
    private String name;
    private String shortName;
    private String created;
    private String lastUpdated;
    private DHISUser createdBy;
    private DHISUser lastUpdatedBy;

    public AbstractDTO(
        String id,
        String name,
        String shortName,
        String created,
        String lastUpdated,
        DHISUser createdBy,
        DHISUser lastUpdatedBy
    ) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AbstractDTO() {}

    public AbstractDTO(String id, String name, String created, String lastUpdated, DHISUser createdBy, DHISUser lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AbstractDTO(String id, String created, String lastUpdated, DHISUser createdBy, DHISUser lastUpdatedBy) {
        this.id = id;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AbstractDTO(String id, String name, String created, String lastUpdated) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public DHISUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DHISUser createdBy) {
        this.createdBy = createdBy;
    }

    public DHISUser getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
