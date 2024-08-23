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
 * A ProgramIndicator.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "program_indicator")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramIndicator implements Serializable, Persistable<String> {

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

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "dimension_item_type")
    private String dimensionItemType;

    @Column(name = "expression")
    private String expression;

    @Column(name = "filter")
    private String filter;

    @Column(name = "analytics_type")
    private String analyticsType;

    @Column(name = "dimension_item")
    private String dimensionItem;

    @Column(name = "display_short_name")
    private String displayShortName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_form_name")
    private String displayFormName;

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

    @ManyToMany(mappedBy = "programIndicators")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProgramIndicator id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProgramIndicator name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ProgramIndicator created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public ProgramIndicator lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getShortName() {
        return this.shortName;
    }

    public ProgramIndicator shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDimensionItemType() {
        return this.dimensionItemType;
    }

    public ProgramIndicator dimensionItemType(String dimensionItemType) {
        this.setDimensionItemType(dimensionItemType);
        return this;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getExpression() {
        return this.expression;
    }

    public ProgramIndicator expression(String expression) {
        this.setExpression(expression);
        return this;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFilter() {
        return this.filter;
    }

    public ProgramIndicator filter(String filter) {
        this.setFilter(filter);
        return this;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getAnalyticsType() {
        return this.analyticsType;
    }

    public ProgramIndicator analyticsType(String analyticsType) {
        this.setAnalyticsType(analyticsType);
        return this;
    }

    public void setAnalyticsType(String analyticsType) {
        this.analyticsType = analyticsType;
    }

    public String getDimensionItem() {
        return this.dimensionItem;
    }

    public ProgramIndicator dimensionItem(String dimensionItem) {
        this.setDimensionItem(dimensionItem);
        return this;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public ProgramIndicator displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ProgramIndicator displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public ProgramIndicator displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public ProgramIndicator track(TypeTrack track) {
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

    public ProgramIndicator setIsPersisted() {
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

    public ProgramIndicator createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public ProgramIndicator lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ProgramIndicator program(Program program) {
        this.setProgram(program);
        return this;
    }

    public Set<Program> getPrograms() {
        return this.programs;
    }

    public void setPrograms(Set<Program> programs) {
        if (this.programs != null) {
            this.programs.forEach(i -> i.removeProgramIndicators(this));
        }
        if (programs != null) {
            programs.forEach(i -> i.addProgramIndicators(this));
        }
        this.programs = programs;
    }

    public ProgramIndicator programs(Set<Program> programs) {
        this.setPrograms(programs);
        return this;
    }

    public ProgramIndicator addPrograms(Program program) {
        this.programs.add(program);
        program.getProgramIndicators().add(this);
        return this;
    }

    public ProgramIndicator removePrograms(Program program) {
        this.programs.remove(program);
        program.getProgramIndicators().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramIndicator)) {
            return false;
        }
        return id != null && id.equals(((ProgramIndicator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramIndicator{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", dimensionItemType='" + getDimensionItemType() + "'" +
            ", expression='" + getExpression() + "'" +
            ", filter='" + getFilter() + "'" +
            ", analyticsType='" + getAnalyticsType() + "'" +
            ", dimensionItem='" + getDimensionItem() + "'" +
            ", displayShortName='" + getDisplayShortName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
