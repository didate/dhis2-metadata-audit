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
 * A Dataelement.
 */
@Entity
@Table(name = "dataelement")
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dataelement implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "description")
    private String description;

    @Column(name = "display_short_name")
    private String displayShortName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "display_form_name")
    private String displayFormName;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @NotNull
    @Column(name = "public_access", nullable = false)
    private String publicAccess;

    @NotNull
    @Column(name = "dimension_item_type", nullable = false)
    private String dimensionItemType;

    @NotNull
    @Column(name = "aggregation_type", nullable = false)
    private String aggregationType;

    @NotNull
    @Column(name = "value_type", nullable = false)
    private String valueType;

    @NotNull
    @Column(name = "domain_type", nullable = false)
    private String domainType;

    @Column(name = "zero_is_significant")
    private Boolean zeroIsSignificant;

    @Column(name = "option_set_value")
    private String optionSetValue;

    @Column(name = "dimension_item")
    private String dimensionItem;

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
    private Categorycombo categoryCombo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Optionset optionSet;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataElements")
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "dataElements", "organisationUnits" },
        allowSetters = true
    )
    private Set<Program> programs = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dataElements")
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "dataElements", "organisationUnits" },
        allowSetters = true
    )
    private Set<Dataset> datasets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Dataelement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Dataelement name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Dataelement shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFormName() {
        return this.formName;
    }

    public Dataelement formName(String formName) {
        this.setFormName(formName);
        return this;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDescription() {
        return this.description;
    }

    public Dataelement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public Dataelement displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Dataelement displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public Dataelement displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Dataelement created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public Dataelement lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPublicAccess() {
        return this.publicAccess;
    }

    public Dataelement publicAccess(String publicAccess) {
        this.setPublicAccess(publicAccess);
        return this;
    }

    public void setPublicAccess(String publicAccess) {
        this.publicAccess = publicAccess;
    }

    public String getDimensionItemType() {
        return this.dimensionItemType;
    }

    public Dataelement dimensionItemType(String dimensionItemType) {
        this.setDimensionItemType(dimensionItemType);
        return this;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getAggregationType() {
        return this.aggregationType;
    }

    public Dataelement aggregationType(String aggregationType) {
        this.setAggregationType(aggregationType);
        return this;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getValueType() {
        return this.valueType;
    }

    public Dataelement valueType(String valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDomainType() {
        return this.domainType;
    }

    public Dataelement domainType(String domainType) {
        this.setDomainType(domainType);
        return this;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public Boolean getZeroIsSignificant() {
        return this.zeroIsSignificant;
    }

    public Dataelement zeroIsSignificant(Boolean zeroIsSignificant) {
        this.setZeroIsSignificant(zeroIsSignificant);
        return this;
    }

    public void setZeroIsSignificant(Boolean zeroIsSignificant) {
        this.zeroIsSignificant = zeroIsSignificant;
    }

    public String getOptionSetValue() {
        return this.optionSetValue;
    }

    public Dataelement optionSetValue(String optionSetValue) {
        this.setOptionSetValue(optionSetValue);
        return this;
    }

    public void setOptionSetValue(String optionSetValue) {
        this.optionSetValue = optionSetValue;
    }

    public String getDimensionItem() {
        return this.dimensionItem;
    }

    public Dataelement dimensionItem(String dimensionItem) {
        this.setDimensionItem(dimensionItem);
        return this;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public Dataelement track(TypeTrack track) {
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

    public Dataelement project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public Dataelement createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public Dataelement lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Categorycombo getCategoryCombo() {
        return this.categoryCombo;
    }

    public void setCategoryCombo(Categorycombo categorycombo) {
        this.categoryCombo = categorycombo;
    }

    public Dataelement categoryCombo(Categorycombo categorycombo) {
        this.setCategoryCombo(categorycombo);
        return this;
    }

    public Optionset getOptionSet() {
        return this.optionSet;
    }

    public void setOptionSet(Optionset optionset) {
        this.optionSet = optionset;
    }

    public Dataelement optionSet(Optionset optionset) {
        this.setOptionSet(optionset);
        return this;
    }

    public Set<Program> getPrograms() {
        return this.programs;
    }

    public void setPrograms(Set<Program> programs) {
        if (this.programs != null) {
            this.programs.forEach(i -> i.removeDataElements(this));
        }
        if (programs != null) {
            programs.forEach(i -> i.addDataElements(this));
        }
        this.programs = programs;
    }

    public Dataelement programs(Set<Program> programs) {
        this.setPrograms(programs);
        return this;
    }

    public Dataelement addProgram(Program program) {
        this.programs.add(program);
        program.getDataElements().add(this);
        return this;
    }

    public Dataelement removeProgram(Program program) {
        this.programs.remove(program);
        program.getDataElements().remove(this);
        return this;
    }

    public Set<Dataset> getDatasets() {
        return this.datasets;
    }

    public void setDatasets(Set<Dataset> datasets) {
        if (this.datasets != null) {
            this.datasets.forEach(i -> i.removeDataElements(this));
        }
        if (datasets != null) {
            datasets.forEach(i -> i.addDataElements(this));
        }
        this.datasets = datasets;
    }

    public Dataelement datasets(Set<Dataset> datasets) {
        this.setDatasets(datasets);
        return this;
    }

    public Dataelement addDataset(Dataset dataset) {
        this.datasets.add(dataset);
        dataset.getDataElements().add(this);
        return this;
    }

    public Dataelement removeDataset(Dataset dataset) {
        this.datasets.remove(dataset);
        dataset.getDataElements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dataelement)) {
            return false;
        }
        return getId() != null && getId().equals(((Dataelement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dataelement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", formName='" + getFormName() + "'" +
            ", description='" + getDescription() + "'" +
            ", displayShortName='" + getDisplayShortName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", publicAccess='" + getPublicAccess() + "'" +
            ", dimensionItemType='" + getDimensionItemType() + "'" +
            ", aggregationType='" + getAggregationType() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", domainType='" + getDomainType() + "'" +
            ", zeroIsSignificant='" + getZeroIsSignificant() + "'" +
            ", optionSetValue='" + getOptionSetValue() + "'" +
            ", dimensionItem='" + getDimensionItem() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
