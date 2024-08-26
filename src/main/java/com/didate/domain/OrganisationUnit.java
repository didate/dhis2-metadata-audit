package com.didate.domain;

import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.domain.Persistable;

/**
 * A OrganisationUnit.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "organisation_unit")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganisationUnit implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @Column(name = "path")
    private String path;

    @NotNull
    @Column(name = "opening_date", nullable = false)
    private Instant openingDate;

    @Column(name = "level")
    private Integer level;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @Transient
    private boolean isPersisted;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToMany(mappedBy = "organisationUnits")
    @JsonIgnoreProperties(
        value = {
            "project",
            "createdBy",
            "lastUpdatedBy",
            "categoryCombo",
            "programTrackedEntityAttributes",
            "organisationUnits",
            "programIndicators",
            "programStages",
        },
        allowSetters = true
    )
    private Set<Program> programs = new HashSet<>();

    @ManyToMany(mappedBy = "organisationUnits")
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "dataSetElements", "indicators", "organisationUnits" },
        allowSetters = true
    )
    private Set<DataSet> dataSets = new HashSet<>();

    @ManyToOne
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public OrganisationUnit id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public OrganisationUnit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return this.created;
    }

    public OrganisationUnit created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public OrganisationUnit lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPath() {
        return this.path;
    }

    public OrganisationUnit path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getOpeningDate() {
        return this.openingDate;
    }

    public OrganisationUnit openingDate(Instant openingDate) {
        this.setOpeningDate(openingDate);
        return this;
    }

    public void setOpeningDate(Instant openingDate) {
        this.openingDate = openingDate;
    }

    public Integer getLevel() {
        return this.level;
    }

    public OrganisationUnit level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public OrganisationUnit track(TypeTrack track) {
        this.setTrack(track);
        return this;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public OrganisationUnit project(Project project) {
        this.setProject(project);
        return this;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public OrganisationUnit setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public OrganisationUnit createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public OrganisationUnit lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Set<Program> getPrograms() {
        return this.programs;
    }

    public void setPrograms(Set<Program> programs) {
        if (this.programs != null) {
            this.programs.forEach(i -> i.removeOrganisationUnits(this));
        }
        if (programs != null) {
            programs.forEach(i -> i.addOrganisationUnits(this));
        }
        this.programs = programs;
    }

    public OrganisationUnit programs(Set<Program> programs) {
        this.setPrograms(programs);
        return this;
    }

    public OrganisationUnit addPrograms(Program program) {
        this.programs.add(program);
        program.getOrganisationUnits().add(this);
        return this;
    }

    public OrganisationUnit removePrograms(Program program) {
        this.programs.remove(program);
        program.getOrganisationUnits().remove(this);
        return this;
    }

    public Set<DataSet> getDataSets() {
        return this.dataSets;
    }

    public void setDataSets(Set<DataSet> datasets) {
        if (this.dataSets != null) {
            this.dataSets.forEach(i -> i.removeOrganisationUnits(this));
        }
        if (datasets != null) {
            datasets.forEach(i -> i.addOrganisationUnits(this));
        }
        this.dataSets = datasets;
    }

    public OrganisationUnit dataSets(Set<DataSet> datasets) {
        this.setDataSets(datasets);
        return this;
    }

    public OrganisationUnit addDataSets(DataSet dataset) {
        this.dataSets.add(dataset);
        dataset.getOrganisationUnits().add(this);
        return this;
    }

    public OrganisationUnit removeDataSets(DataSet dataset) {
        this.dataSets.remove(dataset);
        dataset.getOrganisationUnits().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationUnit)) {
            return false;
        }
        return id != null && id.equals(((OrganisationUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", path='" + getPath() + "'" +
            ", openingDate='" + getOpeningDate() + "'" +
            ", level=" + getLevel() +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
