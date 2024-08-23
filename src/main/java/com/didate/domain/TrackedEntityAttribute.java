package com.didate.domain;

import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.envers.Audited;

/**
 * A TrackedEntityAttribute.
 */
@Entity
@Table(name = "tracked_entity_attribute")
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrackedEntityAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "created")
    private Instant created;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "generated")
    private Boolean generated;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "confidential")
    private Boolean confidential;

    @Column(name = "display_form_name")
    private String displayFormName;

    @Column(name = "uniquee")
    private Boolean uniquee;

    @Column(name = "dimension_item_type")
    private String dimensionItemType;

    @Column(name = "aggregation_type")
    private String aggregationType;

    @Column(name = "display_in_list_no_program")
    private Boolean displayInListNoProgram;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "patterne")
    private String patterne;

    @Column(name = "skip_synchronization")
    private Boolean skipSynchronization;

    @Column(name = "display_short_name")
    private String displayShortName;

    @Column(name = "period_offset")
    private Integer periodOffset;

    @Column(name = "display_on_visit_schedule")
    private Boolean displayOnVisitSchedule;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "orgunit_scope")
    private Boolean orgunitScope;

    @Column(name = "dimension_item")
    private String dimensionItem;

    @Column(name = "inherit")
    private Boolean inherit;

    @Column(name = "option_set_value")
    private Boolean optionSetValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Optionset optionSet;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "programTrackedEntityAttributes")
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

    public TrackedEntityAttribute id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public TrackedEntityAttribute lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Instant getCreated() {
        return this.created;
    }

    public TrackedEntityAttribute created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public TrackedEntityAttribute name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public TrackedEntityAttribute shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getGenerated() {
        return this.generated;
    }

    public TrackedEntityAttribute generated(Boolean generated) {
        this.setGenerated(generated);
        return this;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public String getValueType() {
        return this.valueType;
    }

    public TrackedEntityAttribute valueType(String valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Boolean getConfidential() {
        return this.confidential;
    }

    public TrackedEntityAttribute confidential(Boolean confidential) {
        this.setConfidential(confidential);
        return this;
    }

    public void setConfidential(Boolean confidential) {
        this.confidential = confidential;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public TrackedEntityAttribute displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public Boolean getUniquee() {
        return this.uniquee;
    }

    public TrackedEntityAttribute uniquee(Boolean uniquee) {
        this.setUniquee(uniquee);
        return this;
    }

    public void setUniquee(Boolean uniquee) {
        this.uniquee = uniquee;
    }

    public String getDimensionItemType() {
        return this.dimensionItemType;
    }

    public TrackedEntityAttribute dimensionItemType(String dimensionItemType) {
        this.setDimensionItemType(dimensionItemType);
        return this;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getAggregationType() {
        return this.aggregationType;
    }

    public TrackedEntityAttribute aggregationType(String aggregationType) {
        this.setAggregationType(aggregationType);
        return this;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public Boolean getDisplayInListNoProgram() {
        return this.displayInListNoProgram;
    }

    public TrackedEntityAttribute displayInListNoProgram(Boolean displayInListNoProgram) {
        this.setDisplayInListNoProgram(displayInListNoProgram);
        return this;
    }

    public void setDisplayInListNoProgram(Boolean displayInListNoProgram) {
        this.displayInListNoProgram = displayInListNoProgram;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public TrackedEntityAttribute displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPatterne() {
        return this.patterne;
    }

    public TrackedEntityAttribute patterne(String patterne) {
        this.setPatterne(patterne);
        return this;
    }

    public void setPatterne(String patterne) {
        this.patterne = patterne;
    }

    public Boolean getSkipSynchronization() {
        return this.skipSynchronization;
    }

    public TrackedEntityAttribute skipSynchronization(Boolean skipSynchronization) {
        this.setSkipSynchronization(skipSynchronization);
        return this;
    }

    public void setSkipSynchronization(Boolean skipSynchronization) {
        this.skipSynchronization = skipSynchronization;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public TrackedEntityAttribute displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public TrackedEntityAttribute periodOffset(Integer periodOffset) {
        this.setPeriodOffset(periodOffset);
        return this;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public Boolean getDisplayOnVisitSchedule() {
        return this.displayOnVisitSchedule;
    }

    public TrackedEntityAttribute displayOnVisitSchedule(Boolean displayOnVisitSchedule) {
        this.setDisplayOnVisitSchedule(displayOnVisitSchedule);
        return this;
    }

    public void setDisplayOnVisitSchedule(Boolean displayOnVisitSchedule) {
        this.displayOnVisitSchedule = displayOnVisitSchedule;
    }

    public String getFormName() {
        return this.formName;
    }

    public TrackedEntityAttribute formName(String formName) {
        this.setFormName(formName);
        return this;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Boolean getOrgunitScope() {
        return this.orgunitScope;
    }

    public TrackedEntityAttribute orgunitScope(Boolean orgunitScope) {
        this.setOrgunitScope(orgunitScope);
        return this;
    }

    public void setOrgunitScope(Boolean orgunitScope) {
        this.orgunitScope = orgunitScope;
    }

    public String getDimensionItem() {
        return this.dimensionItem;
    }

    public TrackedEntityAttribute dimensionItem(String dimensionItem) {
        this.setDimensionItem(dimensionItem);
        return this;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public Boolean getInherit() {
        return this.inherit;
    }

    public TrackedEntityAttribute inherit(Boolean inherit) {
        this.setInherit(inherit);
        return this;
    }

    public void setInherit(Boolean inherit) {
        this.inherit = inherit;
    }

    public Boolean getOptionSetValue() {
        return this.optionSetValue;
    }

    public TrackedEntityAttribute optionSetValue(Boolean optionSetValue) {
        this.setOptionSetValue(optionSetValue);
        return this;
    }

    public void setOptionSetValue(Boolean optionSetValue) {
        this.optionSetValue = optionSetValue;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public TrackedEntityAttribute track(TypeTrack track) {
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

    public TrackedEntityAttribute project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public TrackedEntityAttribute createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public TrackedEntityAttribute lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Optionset getOptionSet() {
        return this.optionSet;
    }

    public void setOptionSet(Optionset optionset) {
        this.optionSet = optionset;
    }

    public TrackedEntityAttribute optionSet(Optionset optionset) {
        this.setOptionSet(optionset);
        return this;
    }

    public Set<Program> getPrograms() {
        return this.programs;
    }

    public void setPrograms(Set<Program> programs) {
        if (this.programs != null) {
            this.programs.forEach(i -> i.removeProgramTrackedEntityAttributes(this));
        }
        if (programs != null) {
            programs.forEach(i -> i.addProgramTrackedEntityAttributes(this));
        }
        this.programs = programs;
    }

    public TrackedEntityAttribute programs(Set<Program> programs) {
        this.setPrograms(programs);
        return this;
    }

    public TrackedEntityAttribute addProgram(Program program) {
        this.programs.add(program);
        program.getProgramTrackedEntityAttributes().add(this);
        return this;
    }

    public TrackedEntityAttribute removeProgram(Program program) {
        this.programs.remove(program);
        program.getProgramTrackedEntityAttributes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackedEntityAttribute)) {
            return false;
        }
        return getId() != null && getId().equals(((TrackedEntityAttribute) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrackedEntityAttribute{" +
            "id=" + getId() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", created='" + getCreated() + "'" +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", generated='" + getGenerated() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", confidential='" + getConfidential() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", uniquee='" + getUniquee() + "'" +
            ", dimensionItemType='" + getDimensionItemType() + "'" +
            ", aggregationType='" + getAggregationType() + "'" +
            ", displayInListNoProgram='" + getDisplayInListNoProgram() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", patterne='" + getPatterne() + "'" +
            ", skipSynchronization='" + getSkipSynchronization() + "'" +
            ", displayShortName='" + getDisplayShortName() + "'" +
            ", periodOffset=" + getPeriodOffset() +
            ", displayOnVisitSchedule='" + getDisplayOnVisitSchedule() + "'" +
            ", formName='" + getFormName() + "'" +
            ", orgunitScope='" + getOrgunitScope() + "'" +
            ", dimensionItem='" + getDimensionItem() + "'" +
            ", inherit='" + getInherit() + "'" +
            ", optionSetValue='" + getOptionSetValue() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
