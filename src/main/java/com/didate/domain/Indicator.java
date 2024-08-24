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
 * A Indicator.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "indicator")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Indicator implements Serializable, Persistable<String> {

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
    @Column(name = "annualized", nullable = false)
    private Boolean annualized;

    @Column(name = "numerator")
    private String numerator;

    @Column(name = "numerator_description")
    private String numeratorDescription;

    @Column(name = "denominator")
    private String denominator;

    @Column(name = "denominator_description")
    private String denominatorDescription;

    @Column(name = "display_numerator_description")
    private String displayNumeratorDescription;

    @Column(name = "display_denominator_description")
    private String displayDenominatorDescription;

    @Column(name = "dimension_item")
    private String dimensionItem;

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
    private IndicatorType indicatorType;

    @ManyToMany(mappedBy = "indicators")
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "dataSetElements", "indicators", "organisationUnits" },
        allowSetters = true
    )
    private Set<DataSet> dataSets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Indicator id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Indicator name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Indicator shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public Indicator displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Indicator displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public Indicator displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Indicator created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public Indicator lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPublicAccess() {
        return this.publicAccess;
    }

    public Indicator publicAccess(String publicAccess) {
        this.setPublicAccess(publicAccess);
        return this;
    }

    public void setPublicAccess(String publicAccess) {
        this.publicAccess = publicAccess;
    }

    public String getDimensionItemType() {
        return this.dimensionItemType;
    }

    public Indicator dimensionItemType(String dimensionItemType) {
        this.setDimensionItemType(dimensionItemType);
        return this;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public Boolean getAnnualized() {
        return this.annualized;
    }

    public Indicator annualized(Boolean annualized) {
        this.setAnnualized(annualized);
        return this;
    }

    public void setAnnualized(Boolean annualized) {
        this.annualized = annualized;
    }

    public String getNumerator() {
        return this.numerator;
    }

    public Indicator numerator(String numerator) {
        this.setNumerator(numerator);
        return this;
    }

    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    public String getNumeratorDescription() {
        return this.numeratorDescription;
    }

    public Indicator numeratorDescription(String numeratorDescription) {
        this.setNumeratorDescription(numeratorDescription);
        return this;
    }

    public void setNumeratorDescription(String numeratorDescription) {
        this.numeratorDescription = numeratorDescription;
    }

    public String getDenominator() {
        return this.denominator;
    }

    public Indicator denominator(String denominator) {
        this.setDenominator(denominator);
        return this;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    public String getDenominatorDescription() {
        return this.denominatorDescription;
    }

    public Indicator denominatorDescription(String denominatorDescription) {
        this.setDenominatorDescription(denominatorDescription);
        return this;
    }

    public void setDenominatorDescription(String denominatorDescription) {
        this.denominatorDescription = denominatorDescription;
    }

    public String getDisplayNumeratorDescription() {
        return this.displayNumeratorDescription;
    }

    public Indicator displayNumeratorDescription(String displayNumeratorDescription) {
        this.setDisplayNumeratorDescription(displayNumeratorDescription);
        return this;
    }

    public void setDisplayNumeratorDescription(String displayNumeratorDescription) {
        this.displayNumeratorDescription = displayNumeratorDescription;
    }

    public String getDisplayDenominatorDescription() {
        return this.displayDenominatorDescription;
    }

    public Indicator displayDenominatorDescription(String displayDenominatorDescription) {
        this.setDisplayDenominatorDescription(displayDenominatorDescription);
        return this;
    }

    public void setDisplayDenominatorDescription(String displayDenominatorDescription) {
        this.displayDenominatorDescription = displayDenominatorDescription;
    }

    public String getDimensionItem() {
        return this.dimensionItem;
    }

    public Indicator dimensionItem(String dimensionItem) {
        this.setDimensionItem(dimensionItem);
        return this;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public Indicator track(TypeTrack track) {
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

    public Indicator setIsPersisted() {
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

    public Indicator project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public Indicator createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public Indicator lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public IndicatorType getIndicatorType() {
        return this.indicatorType;
    }

    public void setIndicatorType(IndicatorType indicatortype) {
        this.indicatorType = indicatortype;
    }

    public Indicator indicatorType(IndicatorType indicatortype) {
        this.setIndicatorType(indicatortype);
        return this;
    }

    public Set<DataSet> getDataSets() {
        return this.dataSets;
    }

    public void setDataSets(Set<DataSet> datasets) {
        if (this.dataSets != null) {
            this.dataSets.forEach(i -> i.removeIndicators(this));
        }
        if (datasets != null) {
            datasets.forEach(i -> i.addIndicators(this));
        }
        this.dataSets = datasets;
    }

    public Indicator dataSets(Set<DataSet> datasets) {
        this.setDataSets(datasets);
        return this;
    }

    public Indicator addDataSets(DataSet dataset) {
        this.dataSets.add(dataset);
        dataset.getIndicators().add(this);
        return this;
    }

    public Indicator removeDataSets(DataSet dataset) {
        this.dataSets.remove(dataset);
        dataset.getIndicators().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Indicator)) {
            return false;
        }
        return id != null && id.equals(((Indicator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Indicator{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", displayShortName='" + getDisplayShortName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", publicAccess='" + getPublicAccess() + "'" +
            ", dimensionItemType='" + getDimensionItemType() + "'" +
            ", annualized='" + getAnnualized() + "'" +
            ", numerator='" + getNumerator() + "'" +
            ", numeratorDescription='" + getNumeratorDescription() + "'" +
            ", denominator='" + getDenominator() + "'" +
            ", denominatorDescription='" + getDenominatorDescription() + "'" +
            ", displayNumeratorDescription='" + getDisplayNumeratorDescription() + "'" +
            ", displayDenominatorDescription='" + getDisplayDenominatorDescription() + "'" +
            ", dimensionItem='" + getDimensionItem() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
