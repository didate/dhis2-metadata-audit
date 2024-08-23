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
 * A Dataset.
 */
@Entity
@Table(name = "dataset")
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dataset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "description")
    private String description;

    @Column(name = "dimension_item_type")
    private String dimensionItemType;

    @Column(name = "period_type")
    private String periodType;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "version")
    private Double version;

    @Column(name = "expiry_days")
    private Double expiryDays;

    @Column(name = "timely_days")
    private Double timelyDays;

    @Column(name = "notify_completing_user")
    private String notifyCompletingUser;

    @Column(name = "open_future_periods")
    private Double openFuturePeriods;

    @Column(name = "open_periods_after_co_end_date")
    private Double openPeriodsAfterCoEndDate;

    @Column(name = "field_combination_required")
    private Boolean fieldCombinationRequired;

    @Column(name = "valid_complete_only")
    private Boolean validCompleteOnly;

    @Column(name = "no_value_requires_comment")
    private Boolean noValueRequiresComment;

    @Column(name = "skip_offline")
    private Boolean skipOffline;

    @Column(name = "data_element_decoration")
    private Boolean dataElementDecoration;

    @Column(name = "render_as_tabs")
    private Boolean renderAsTabs;

    @Column(name = "render_horizontally")
    private Boolean renderHorizontally;

    @Column(name = "compulsory_fields_complete_only")
    private Boolean compulsoryFieldsCompleteOnly;

    @Column(name = "form_type")
    private String formType;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "dimension_item")
    private String dimensionItem;

    @Column(name = "display_short_name")
    private String displayShortName;

    @Column(name = "display_description")
    private String displayDescription;

    @Column(name = "display_form_name")
    private String displayFormName;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_dataset__data_elements",
        joinColumns = @JoinColumn(name = "dataset_id"),
        inverseJoinColumns = @JoinColumn(name = "data_elements_id")
    )
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "optionSet", "programs", "datasets" },
        allowSetters = true
    )
    private Set<Dataelement> dataElements = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_dataset__organisation_units",
        joinColumns = @JoinColumn(name = "dataset_id"),
        inverseJoinColumns = @JoinColumn(name = "organisation_units_id")
    )
    @JsonIgnoreProperties(value = { "createdBy", "lastUpdatedBy", "programs", "datasets" }, allowSetters = true)
    private Set<OrganisationUnit> organisationUnits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Dataset id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Dataset name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Dataset created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public Dataset lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Dataset shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return this.description;
    }

    public Dataset description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensionItemType() {
        return this.dimensionItemType;
    }

    public Dataset dimensionItemType(String dimensionItemType) {
        this.setDimensionItemType(dimensionItemType);
        return this;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public Dataset periodType(String periodType) {
        this.setPeriodType(periodType);
        return this;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Dataset mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getVersion() {
        return this.version;
    }

    public Dataset version(Double version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Double getExpiryDays() {
        return this.expiryDays;
    }

    public Dataset expiryDays(Double expiryDays) {
        this.setExpiryDays(expiryDays);
        return this;
    }

    public void setExpiryDays(Double expiryDays) {
        this.expiryDays = expiryDays;
    }

    public Double getTimelyDays() {
        return this.timelyDays;
    }

    public Dataset timelyDays(Double timelyDays) {
        this.setTimelyDays(timelyDays);
        return this;
    }

    public void setTimelyDays(Double timelyDays) {
        this.timelyDays = timelyDays;
    }

    public String getNotifyCompletingUser() {
        return this.notifyCompletingUser;
    }

    public Dataset notifyCompletingUser(String notifyCompletingUser) {
        this.setNotifyCompletingUser(notifyCompletingUser);
        return this;
    }

    public void setNotifyCompletingUser(String notifyCompletingUser) {
        this.notifyCompletingUser = notifyCompletingUser;
    }

    public Double getOpenFuturePeriods() {
        return this.openFuturePeriods;
    }

    public Dataset openFuturePeriods(Double openFuturePeriods) {
        this.setOpenFuturePeriods(openFuturePeriods);
        return this;
    }

    public void setOpenFuturePeriods(Double openFuturePeriods) {
        this.openFuturePeriods = openFuturePeriods;
    }

    public Double getOpenPeriodsAfterCoEndDate() {
        return this.openPeriodsAfterCoEndDate;
    }

    public Dataset openPeriodsAfterCoEndDate(Double openPeriodsAfterCoEndDate) {
        this.setOpenPeriodsAfterCoEndDate(openPeriodsAfterCoEndDate);
        return this;
    }

    public void setOpenPeriodsAfterCoEndDate(Double openPeriodsAfterCoEndDate) {
        this.openPeriodsAfterCoEndDate = openPeriodsAfterCoEndDate;
    }

    public Boolean getFieldCombinationRequired() {
        return this.fieldCombinationRequired;
    }

    public Dataset fieldCombinationRequired(Boolean fieldCombinationRequired) {
        this.setFieldCombinationRequired(fieldCombinationRequired);
        return this;
    }

    public void setFieldCombinationRequired(Boolean fieldCombinationRequired) {
        this.fieldCombinationRequired = fieldCombinationRequired;
    }

    public Boolean getValidCompleteOnly() {
        return this.validCompleteOnly;
    }

    public Dataset validCompleteOnly(Boolean validCompleteOnly) {
        this.setValidCompleteOnly(validCompleteOnly);
        return this;
    }

    public void setValidCompleteOnly(Boolean validCompleteOnly) {
        this.validCompleteOnly = validCompleteOnly;
    }

    public Boolean getNoValueRequiresComment() {
        return this.noValueRequiresComment;
    }

    public Dataset noValueRequiresComment(Boolean noValueRequiresComment) {
        this.setNoValueRequiresComment(noValueRequiresComment);
        return this;
    }

    public void setNoValueRequiresComment(Boolean noValueRequiresComment) {
        this.noValueRequiresComment = noValueRequiresComment;
    }

    public Boolean getSkipOffline() {
        return this.skipOffline;
    }

    public Dataset skipOffline(Boolean skipOffline) {
        this.setSkipOffline(skipOffline);
        return this;
    }

    public void setSkipOffline(Boolean skipOffline) {
        this.skipOffline = skipOffline;
    }

    public Boolean getDataElementDecoration() {
        return this.dataElementDecoration;
    }

    public Dataset dataElementDecoration(Boolean dataElementDecoration) {
        this.setDataElementDecoration(dataElementDecoration);
        return this;
    }

    public void setDataElementDecoration(Boolean dataElementDecoration) {
        this.dataElementDecoration = dataElementDecoration;
    }

    public Boolean getRenderAsTabs() {
        return this.renderAsTabs;
    }

    public Dataset renderAsTabs(Boolean renderAsTabs) {
        this.setRenderAsTabs(renderAsTabs);
        return this;
    }

    public void setRenderAsTabs(Boolean renderAsTabs) {
        this.renderAsTabs = renderAsTabs;
    }

    public Boolean getRenderHorizontally() {
        return this.renderHorizontally;
    }

    public Dataset renderHorizontally(Boolean renderHorizontally) {
        this.setRenderHorizontally(renderHorizontally);
        return this;
    }

    public void setRenderHorizontally(Boolean renderHorizontally) {
        this.renderHorizontally = renderHorizontally;
    }

    public Boolean getCompulsoryFieldsCompleteOnly() {
        return this.compulsoryFieldsCompleteOnly;
    }

    public Dataset compulsoryFieldsCompleteOnly(Boolean compulsoryFieldsCompleteOnly) {
        this.setCompulsoryFieldsCompleteOnly(compulsoryFieldsCompleteOnly);
        return this;
    }

    public void setCompulsoryFieldsCompleteOnly(Boolean compulsoryFieldsCompleteOnly) {
        this.compulsoryFieldsCompleteOnly = compulsoryFieldsCompleteOnly;
    }

    public String getFormType() {
        return this.formType;
    }

    public Dataset formType(String formType) {
        this.setFormType(formType);
        return this;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Dataset displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDimensionItem() {
        return this.dimensionItem;
    }

    public Dataset dimensionItem(String dimensionItem) {
        this.setDimensionItem(dimensionItem);
        return this;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public Dataset displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayDescription() {
        return this.displayDescription;
    }

    public Dataset displayDescription(String displayDescription) {
        this.setDisplayDescription(displayDescription);
        return this;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public Dataset displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public Dataset track(TypeTrack track) {
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

    public Dataset project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public Dataset createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public Dataset lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Categorycombo getCategoryCombo() {
        return this.categoryCombo;
    }

    public void setCategoryCombo(Categorycombo categorycombo) {
        this.categoryCombo = categorycombo;
    }

    public Dataset categoryCombo(Categorycombo categorycombo) {
        this.setCategoryCombo(categorycombo);
        return this;
    }

    public Set<Dataelement> getDataElements() {
        return this.dataElements;
    }

    public void setDataElements(Set<Dataelement> dataelements) {
        this.dataElements = dataelements;
    }

    public Dataset dataElements(Set<Dataelement> dataelements) {
        this.setDataElements(dataelements);
        return this;
    }

    public Dataset addDataElements(Dataelement dataelement) {
        this.dataElements.add(dataelement);
        return this;
    }

    public Dataset removeDataElements(Dataelement dataelement) {
        this.dataElements.remove(dataelement);
        return this;
    }

    public Set<OrganisationUnit> getOrganisationUnits() {
        return this.organisationUnits;
    }

    public void setOrganisationUnits(Set<OrganisationUnit> organisationUnits) {
        this.organisationUnits = organisationUnits;
    }

    public Dataset organisationUnits(Set<OrganisationUnit> organisationUnits) {
        this.setOrganisationUnits(organisationUnits);
        return this;
    }

    public Dataset addOrganisationUnits(OrganisationUnit organisationUnit) {
        this.organisationUnits.add(organisationUnit);
        return this;
    }

    public Dataset removeOrganisationUnits(OrganisationUnit organisationUnit) {
        this.organisationUnits.remove(organisationUnit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dataset)) {
            return false;
        }
        return getId() != null && getId().equals(((Dataset) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dataset{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dimensionItemType='" + getDimensionItemType() + "'" +
            ", periodType='" + getPeriodType() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", version=" + getVersion() +
            ", expiryDays=" + getExpiryDays() +
            ", timelyDays=" + getTimelyDays() +
            ", notifyCompletingUser='" + getNotifyCompletingUser() + "'" +
            ", openFuturePeriods=" + getOpenFuturePeriods() +
            ", openPeriodsAfterCoEndDate=" + getOpenPeriodsAfterCoEndDate() +
            ", fieldCombinationRequired='" + getFieldCombinationRequired() + "'" +
            ", validCompleteOnly='" + getValidCompleteOnly() + "'" +
            ", noValueRequiresComment='" + getNoValueRequiresComment() + "'" +
            ", skipOffline='" + getSkipOffline() + "'" +
            ", dataElementDecoration='" + getDataElementDecoration() + "'" +
            ", renderAsTabs='" + getRenderAsTabs() + "'" +
            ", renderHorizontally='" + getRenderHorizontally() + "'" +
            ", compulsoryFieldsCompleteOnly='" + getCompulsoryFieldsCompleteOnly() + "'" +
            ", formType='" + getFormType() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", dimensionItem='" + getDimensionItem() + "'" +
            ", displayShortName='" + getDisplayShortName() + "'" +
            ", displayDescription='" + getDisplayDescription() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
