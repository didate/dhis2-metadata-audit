package com.didate.service.impl;

import com.didate.domain.Dataset;
import com.didate.repository.DatasetRepository;
import com.didate.service.DatasetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.Dataset}.
 */
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

    private static final Logger log = LoggerFactory.getLogger(DatasetServiceImpl.class);

    private final DatasetRepository datasetRepository;

    public DatasetServiceImpl(DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;
    }

    @Override
    public Dataset save(Dataset dataset) {
        log.debug("Request to save Dataset : {}", dataset);
        return datasetRepository.save(dataset);
    }

    @Override
    public Dataset update(Dataset dataset) {
        log.debug("Request to update Dataset : {}", dataset);
        return datasetRepository.save(dataset);
    }

    @Override
    public Optional<Dataset> partialUpdate(Dataset dataset) {
        log.debug("Request to partially update Dataset : {}", dataset);

        return datasetRepository
            .findById(dataset.getId())
            .map(existingDataset -> {
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
                if (dataset.getTrack() != null) {
                    existingDataset.setTrack(dataset.getTrack());
                }

                return existingDataset;
            })
            .map(datasetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dataset> findAll(Pageable pageable) {
        log.debug("Request to get all Datasets");
        return datasetRepository.findAll(pageable);
    }

    public Page<Dataset> findAllWithEagerRelationships(Pageable pageable) {
        return datasetRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dataset> findOne(String id) {
        log.debug("Request to get Dataset : {}", id);
        return datasetRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Dataset : {}", id);
        datasetRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return datasetRepository.existsById(id);
    }
}
