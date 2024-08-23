package com.didate.domain;

import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.domain.Persistable;

/**
 * A ProgramRuleVariable.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "program_rule_variable")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramRuleVariable implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "program_rule_variable_source_type")
    private String programRuleVariableSourceType;

    @Column(name = "use_code_for_option_set")
    private Boolean useCodeForOptionSet;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToOne(optional = false)
    @NotNull
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
    private Program program;

    @ManyToOne
    @JsonIgnoreProperties(value = { "project", "createdBy", "lastUpdatedBy", "optionSet", "programs" }, allowSetters = true)
    private TrackedEntityAttribute trackedEntityAttribute;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "optionSet", "dataSets", "programStages" },
        allowSetters = true
    )
    private Dataelement dataElement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProgramRuleVariable id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public ProgramRuleVariable lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ProgramRuleVariable created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public ProgramRuleVariable name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ProgramRuleVariable displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProgramRuleVariableSourceType() {
        return this.programRuleVariableSourceType;
    }

    public ProgramRuleVariable programRuleVariableSourceType(String programRuleVariableSourceType) {
        this.setProgramRuleVariableSourceType(programRuleVariableSourceType);
        return this;
    }

    public void setProgramRuleVariableSourceType(String programRuleVariableSourceType) {
        this.programRuleVariableSourceType = programRuleVariableSourceType;
    }

    public Boolean getUseCodeForOptionSet() {
        return this.useCodeForOptionSet;
    }

    public ProgramRuleVariable useCodeForOptionSet(Boolean useCodeForOptionSet) {
        this.setUseCodeForOptionSet(useCodeForOptionSet);
        return this;
    }

    public void setUseCodeForOptionSet(Boolean useCodeForOptionSet) {
        this.useCodeForOptionSet = useCodeForOptionSet;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public ProgramRuleVariable track(TypeTrack track) {
        this.setTrack(track);
        return this;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ProgramRuleVariable setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProgramRuleVariable project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public ProgramRuleVariable createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public ProgramRuleVariable lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ProgramRuleVariable program(Program program) {
        this.setProgram(program);
        return this;
    }

    public TrackedEntityAttribute getTrackedEntityAttribute() {
        return this.trackedEntityAttribute;
    }

    public void setTrackedEntityAttribute(TrackedEntityAttribute trackedEntityAttribute) {
        this.trackedEntityAttribute = trackedEntityAttribute;
    }

    public ProgramRuleVariable trackedEntityAttribute(TrackedEntityAttribute trackedEntityAttribute) {
        this.setTrackedEntityAttribute(trackedEntityAttribute);
        return this;
    }

    public Dataelement getDataElement() {
        return this.dataElement;
    }

    public void setDataElement(Dataelement dataelement) {
        this.dataElement = dataelement;
    }

    public ProgramRuleVariable dataElement(Dataelement dataelement) {
        this.setDataElement(dataelement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramRuleVariable)) {
            return false;
        }
        return id != null && id.equals(((ProgramRuleVariable) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramRuleVariable{" +
            "id=" + getId() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", created='" + getCreated() + "'" +
            ", name='" + getName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", programRuleVariableSourceType='" + getProgramRuleVariableSourceType() + "'" +
            ", useCodeForOptionSet='" + getUseCodeForOptionSet() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
