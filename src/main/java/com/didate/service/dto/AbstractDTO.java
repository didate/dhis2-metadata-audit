package com.didate.service.dto;

import com.didate.domain.DHISUser;
import java.time.Instant;

public class AbstractDTO {

    private String id;
    private String name;
    private String shortName;
    private Instant created;
    private Instant lastUpdated;
    private DHISUser createdBy;
    private DHISUser lastUpdatedBy;

    public AbstractDTO(
        String id,
        String name,
        String shortName,
        Instant created,
        Instant lastUpdated,
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

    public AbstractDTO(String id, String name, Instant created, Instant lastUpdated, DHISUser createdBy, DHISUser lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AbstractDTO(String id, Instant created, Instant lastUpdated, DHISUser createdBy, DHISUser lastUpdatedBy) {
        this.id = id;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AbstractDTO(String id, String name, Instant created, Instant lastUpdated) {
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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
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
