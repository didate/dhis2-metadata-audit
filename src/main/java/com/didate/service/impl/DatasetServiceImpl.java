package com.didate.service.impl;

import com.didate.domain.DataSet;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.DatasetRepository;
import com.didate.service.DatasetService;
import com.didate.service.dto.DataSetDTO;
import com.didate.service.dto.DataSetFullDTO;
import com.didate.service.search.GenericFilterService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.RevisionSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DataSet}.
 */
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

    private final Logger log = LoggerFactory.getLogger(DatasetServiceImpl.class);

    private final DatasetRepository datasetRepository;

    private final GenericFilterService<DataSet> filterService;

    public DatasetServiceImpl(DatasetRepository datasetRepository, GenericFilterService<DataSet> filterService) {
        this.datasetRepository = datasetRepository;
        this.filterService = filterService;
    }

    @Override
    public DataSet save(DataSet dataset) {
        log.debug("Request to save DataSet : {}", dataset);
        return datasetRepository.save(dataset);
    }

    @Override
    public DataSet update(DataSet dataset) {
        log.debug("Request to update DataSet : {}", dataset);
        dataset.setIsPersisted();
        return datasetRepository.save(dataset);
    }

    @Override
    public Optional<DataSet> partialUpdate(DataSet dataset) {
        log.debug("Request to partially update DataSet : {}", dataset);

        return datasetRepository
            .findById(dataset.getId())
            .map(existingDataset -> {
                if (existingDataset.getLastUpdated().equals(dataset.getLastUpdated())) {
                    return existingDataset;
                }
                if (dataset.getName() != null) {
                    existingDataset.setName(dataset.getName());
                }
                if (dataset.getCreated() != null) {
                    existingDataset.setCreated(dataset.getCreated());
                }
                if (dataset.getLastUpdated() != null) {
                    existingDataset.setLastUpdated(dataset.getLastUpdated());
                }
                if (dataset.getShortName() != null) {
                    existingDataset.setShortName(dataset.getShortName());
                }
                if (dataset.getDescription() != null) {
                    existingDataset.setDescription(dataset.getDescription());
                }
                if (dataset.getDimensionItemType() != null) {
                    existingDataset.setDimensionItemType(dataset.getDimensionItemType());
                }
                if (dataset.getPeriodType() != null) {
                    existingDataset.setPeriodType(dataset.getPeriodType());
                }
                if (dataset.getMobile() != null) {
                    existingDataset.setMobile(dataset.getMobile());
                }
                if (dataset.getVersion() != null) {
                    existingDataset.setVersion(dataset.getVersion());
                }
                if (dataset.getExpiryDays() != null) {
                    existingDataset.setExpiryDays(dataset.getExpiryDays());
                }
                if (dataset.getTimelyDays() != null) {
                    existingDataset.setTimelyDays(dataset.getTimelyDays());
                }
                if (dataset.getNotifyCompletingUser() != null) {
                    existingDataset.setNotifyCompletingUser(dataset.getNotifyCompletingUser());
                }
                if (dataset.getOpenFuturePeriods() != null) {
                    existingDataset.setOpenFuturePeriods(dataset.getOpenFuturePeriods());
                }
                if (dataset.getOpenPeriodsAfterCoEndDate() != null) {
                    existingDataset.setOpenPeriodsAfterCoEndDate(dataset.getOpenPeriodsAfterCoEndDate());
                }
                if (dataset.getFieldCombinationRequired() != null) {
                    existingDataset.setFieldCombinationRequired(dataset.getFieldCombinationRequired());
                }
                if (dataset.getValidCompleteOnly() != null) {
                    existingDataset.setValidCompleteOnly(dataset.getValidCompleteOnly());
                }
                if (dataset.getNoValueRequiresComment() != null) {
                    existingDataset.setNoValueRequiresComment(dataset.getNoValueRequiresComment());
                }
                if (dataset.getSkipOffline() != null) {
                    existingDataset.setSkipOffline(dataset.getSkipOffline());
                }
                if (dataset.getDataElementDecoration() != null) {
                    existingDataset.setDataElementDecoration(dataset.getDataElementDecoration());
                }
                if (dataset.getRenderAsTabs() != null) {
                    existingDataset.setRenderAsTabs(dataset.getRenderAsTabs());
                }
                if (dataset.getRenderHorizontally() != null) {
                    existingDataset.setRenderHorizontally(dataset.getRenderHorizontally());
                }
                if (dataset.getCompulsoryFieldsCompleteOnly() != null) {
                    existingDataset.setCompulsoryFieldsCompleteOnly(dataset.getCompulsoryFieldsCompleteOnly());
                }
                if (dataset.getFormType() != null) {
                    existingDataset.setFormType(dataset.getFormType());
                }
                if (dataset.getDisplayName() != null) {
                    existingDataset.setDisplayName(dataset.getDisplayName());
                }
                if (dataset.getDimensionItem() != null) {
                    existingDataset.setDimensionItem(dataset.getDimensionItem());
                }
                if (dataset.getDisplayShortName() != null) {
                    existingDataset.setDisplayShortName(dataset.getDisplayShortName());
                }
                if (dataset.getDisplayDescription() != null) {
                    existingDataset.setDisplayDescription(dataset.getDisplayDescription());
                }
                if (dataset.getDisplayFormName() != null) {
                    existingDataset.setDisplayFormName(dataset.getDisplayFormName());
                }
                if (dataset.getDataSetElementsCount() != null) {
                    existingDataset.setDataSetElementsCount(dataset.getDataSetElementsCount());
                }
                if (dataset.getIndicatorsCount() != null) {
                    existingDataset.setIndicatorsCount(dataset.getIndicatorsCount());
                }
                if (dataset.getOrganisationUnitsCount() != null) {
                    existingDataset.setOrganisationUnitsCount(dataset.getOrganisationUnitsCount());
                }
                if (dataset.getDataSetElementsContent() != null) {
                    existingDataset.setDataSetElementsContent(dataset.getDataSetElementsContent());
                }
                if (dataset.getIndicatorsContent() != null) {
                    existingDataset.setIndicatorsContent(dataset.getIndicatorsContent());
                }
                if (dataset.getOrganisationUnitsContent() != null) {
                    existingDataset.setOrganisationUnitsContent(dataset.getOrganisationUnitsContent());
                }
                if (dataset.getTrack() != null) {
                    existingDataset.setTrack(dataset.getTrack());
                }

                if (!dataset.getCreatedBy().equals(existingDataset.getCreatedBy())) {
                    existingDataset.setCreatedBy(dataset.getCreatedBy());
                }
                if (!dataset.getLastUpdatedBy().equals(existingDataset.getLastUpdatedBy())) {
                    existingDataset.setLastUpdatedBy(dataset.getLastUpdatedBy());
                }

                return existingDataset;
            })
            .map(datasetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DataSet> findAll(Pageable pageable) {
        log.debug("Request to get all Datasets");
        return datasetRepository.findAll(pageable);
    }

    public Page<DataSet> findAllWithEagerRelationships(Pageable pageable) {
        return datasetRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DataSet> findOne(String id) {
        log.debug("Request to get DataSet : {}", id);
        return datasetRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete DataSet : {}", id);
        datasetRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return datasetRepository.existsById(id);
    }

    @Override
    public Long count() {
        return datasetRepository.count();
    }

    @Override
    public List<DataSetDTO> findAudits(String id) {
        Pageable pageable = PageRequest.of(0, 20, RevisionSort.desc());

        return datasetRepository
            .findRevisions(id, pageable)
            .getContent()
            .stream()
            .map(revision -> {
                DataSet dataSet = revision.getEntity();
                Hibernate.unproxy(dataSet.getCreatedBy());
                Hibernate.unproxy(dataSet.getLastUpdatedBy());
                return new DataSetDTO(dataSet).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DataSetFullDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        DataSet dataSet = datasetRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(dataSet.getCreatedBy());
        Hibernate.unproxy(dataSet.getLastUpdatedBy());
        Hibernate.unproxy(dataSet.getCategoryCombo());

        return new DataSetFullDTO(dataSet);
    }

    @Override
    public Page<DataSetDTO> findAllDataSets(Pageable pageable) {
        return datasetRepository.findAll(pageable).map(DataSetDTO::new);
    }

    @Override
    public Page<DataSetDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(datasetRepository, id, name, pageable).map(DataSetDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return datasetRepository.countByTrack(track);
    }

    @Override
    public void setTrackNone() {
        datasetRepository.setTrackNone();
    }
}
