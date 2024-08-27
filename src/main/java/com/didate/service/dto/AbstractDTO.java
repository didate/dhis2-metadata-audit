package com.didate.service.dto;

import com.didate.domain.DHISUser;

public class AbstractDTO {

    private String id;
    private String name;
    private String created;
    private String lastUpdated;
    private DHISUser createdBy;
    private DHISUser lastUpdatedBy;

    public AbstractDTO() {}

    public AbstractDTO(String id, String name, String created, String lastUpdated, DHISUser createdBy, DHISUser lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
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
}
