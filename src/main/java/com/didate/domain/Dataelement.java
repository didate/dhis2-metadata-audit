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
 * A Dataelement.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "dataelement")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataElement implements Serializable, Persistable<String> {

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
    @Column(name = "created", nullable = false)
    private Instant created;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToOne
    private Project project;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    private CategoryCombo categoryCombo;

    @ManyToOne
    private OptionSet optionSet;

    @ManyToMany(mappedBy = "dataSetElements")
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "dataSetElements", "indicators", "organisationUnits" },
        allowSetters = true
    )
    private Set<DataSet> dataSets = new HashSet<>();

    @ManyToMany(mappedBy = "programStageDataElements")
    @JsonIgnoreProperties(value = { "createdBy", "lastUpdatedBy", "program", "programStageDataElements", "programs" }, allowSetters = true)
    private Set<ProgramStage> programStages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public DataElement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormName() {
        return this.formName;
    }

    public DataElement formName(String formName) {
        this.setFormName(formName);
        return this;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDescription() {
        return this.description;
    }

    public DataElement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public DataElement displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public DataElement displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public DataElement displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public String getPublicAccess() {
        return this.publicAccess;
    }

    public DataElement publicAccess(String publicAccess) {
        this.setPublicAccess(publicAccess);
        return this;
    }

    public void setPublicAccess(String publicAccess) {
        this.publicAccess = publicAccess;
    }

    public String getDimensionItemType() {
        return this.dimensionItemType;
    }

    public DataElement dimensionItemType(String dimensionItemType) {
        this.setDimensionItemType(dimensionItemType);
        return this;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getAggregationType() {
        return this.aggregationType;
    }

    public DataElement aggregationType(String aggregationType) {
        this.setAggregationType(aggregationType);
        return this;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getValueType() {
        return this.valueType;
    }

    public DataElement valueType(String valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDomainType() {
        return this.domainType;
    }

    public DataElement domainType(String domainType) {
        this.setDomainType(domainType);
        return this;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public Boolean getZeroIsSignificant() {
        return this.zeroIsSignificant;
    }

    public DataElement zeroIsSignificant(Boolean zeroIsSignificant) {
        this.setZeroIsSignificant(zeroIsSignificant);
        return this;
    }

    public void setZeroIsSignificant(Boolean zeroIsSignificant) {
        this.zeroIsSignificant = zeroIsSignificant;
    }

    public String getOptionSetValue() {
        return this.optionSetValue;
    }

    public DataElement optionSetValue(String optionSetValue) {
        this.setOptionSetValue(optionSetValue);
        return this;
    }

    public void setOptionSetValue(String optionSetValue) {
        this.optionSetValue = optionSetValue;
    }

    public String getDimensionItem() {
        return this.dimensionItem;
    }

    public DataElement dimensionItem(String dimensionItem) {
        this.setDimensionItem(dimensionItem);
        return this;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public DataElement track(TypeTrack track) {
        this.setTrack(track);
        return this;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    public String getName() {
        return this.name;
    }

    public DataElement name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public DataElement shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Instant getCreated() {
        return this.created;
    }

    public DataElement created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public DataElement lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public DataElement project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public DataElement createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public DataElement lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public DataElement setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public CategoryCombo getCategoryCombo() {
        return this.categoryCombo;
    }

    public void setCategoryCombo(CategoryCombo categorycombo) {
        this.categoryCombo = categorycombo;
    }

    public DataElement categoryCombo(CategoryCombo categorycombo) {
        this.setCategoryCombo(categorycombo);
        return this;
    }

    public OptionSet getOptionSet() {
        return this.optionSet;
    }

    public void setOptionSet(OptionSet optionset) {
        this.optionSet = optionset;
    }

    public DataElement optionSet(OptionSet optionset) {
        this.setOptionSet(optionset);
        return this;
    }

    public Set<DataSet> getDataSets() {
        return this.dataSets;
    }

    public void setDataSets(Set<DataSet> datasets) {
        if (this.dataSets != null) {
            this.dataSets.forEach(i -> i.removeDataSetElements(this));
        }
        if (datasets != null) {
            datasets.forEach(i -> i.addDataSetElements(this));
        }
        this.dataSets = datasets;
    }

    public DataElement dataSets(Set<DataSet> datasets) {
        this.setDataSets(datasets);
        return this;
    }

    public DataElement addDataSets(DataSet dataset) {
        this.dataSets.add(dataset);
        dataset.getDataSetElements().add(this);
        return this;
    }

    public DataElement removeDataSets(DataSet dataset) {
        this.dataSets.remove(dataset);
        dataset.getDataSetElements().remove(this);
        return this;
    }

    public Set<ProgramStage> getProgramStages() {
        return this.programStages;
    }

    public void setProgramStages(Set<ProgramStage> programStages) {
        if (this.programStages != null) {
            this.programStages.forEach(i -> i.removeProgramStageDataElements(this));
        }
        if (programStages != null) {
            programStages.forEach(i -> i.addProgramStageDataElements(this));
        }
        this.programStages = programStages;
    }

    public DataElement programStages(Set<ProgramStage> programStages) {
        this.setProgramStages(programStages);
        return this;
    }

    public DataElement addProgramStages(ProgramStage programStage) {
        this.programStages.add(programStage);
        programStage.getProgramStageDataElements().add(this);
        return this;
    }

    public DataElement removeProgramStages(ProgramStage programStage) {
        this.programStages.remove(programStage);
        programStage.getProgramStageDataElements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataElement)) {
            return false;
        }
        return id != null && id.equals(((DataElement) o).id);
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
