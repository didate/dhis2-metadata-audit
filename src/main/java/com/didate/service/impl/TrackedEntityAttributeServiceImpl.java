package com.didate.service.impl;

import com.didate.domain.TrackedEntityAttribute;
import com.didate.repository.TrackedEntityAttributeRepository;
import com.didate.service.TrackedEntityAttributeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.TrackedEntityAttribute}.
 */
@Service
@Transactional
public class TrackedEntityAttributeServiceImpl implements TrackedEntityAttributeService {

    private static final Logger log = LoggerFactory.getLogger(TrackedEntityAttributeServiceImpl.class);

    private final TrackedEntityAttributeRepository trackedEntityAttributeRepository;

    public TrackedEntityAttributeServiceImpl(TrackedEntityAttributeRepository trackedEntityAttributeRepository) {
        this.trackedEntityAttributeRepository = trackedEntityAttributeRepository;
    }

    @Override
    public TrackedEntityAttribute save(TrackedEntityAttribute trackedEntityAttribute) {
        log.debug("Request to save TrackedEntityAttribute : {}", trackedEntityAttribute);
        return trackedEntityAttributeRepository.save(trackedEntityAttribute);
    }

    @Override
    public TrackedEntityAttribute update(TrackedEntityAttribute trackedEntityAttribute) {
        log.debug("Request to update TrackedEntityAttribute : {}", trackedEntityAttribute);
        return trackedEntityAttributeRepository.save(trackedEntityAttribute);
    }

    @Override
    public Optional<TrackedEntityAttribute> partialUpdate(TrackedEntityAttribute trackedEntityAttribute) {
        log.debug("Request to partially update TrackedEntityAttribute : {}", trackedEntityAttribute);

        return trackedEntityAttributeRepository
            .findById(trackedEntityAttribute.getId())
            .map(existingTrackedEntityAttribute -> {
                if (trackedEntityAttribute.getLastUpdated() != null) {
                    existingTrackedEntityAttribute.setLastUpdated(trackedEntityAttribute.getLastUpdated());
                }
                if (trackedEntityAttribute.getCreated() != null) {
                    existingTrackedEntityAttribute.setCreated(trackedEntityAttribute.getCreated());
                }
                if (trackedEntityAttribute.getName() != null) {
                    existingTrackedEntityAttribute.setName(trackedEntityAttribute.getName());
                }
                if (trackedEntityAttribute.getShortName() != null) {
                    existingTrackedEntityAttribute.setShortName(trackedEntityAttribute.getShortName());
                }
                if (trackedEntityAttribute.getGenerated() != null) {
                    existingTrackedEntityAttribute.setGenerated(trackedEntityAttribute.getGenerated());
                }
                if (trackedEntityAttribute.getValueType() != null) {
                    existingTrackedEntityAttribute.setValueType(trackedEntityAttribute.getValueType());
                }
                if (trackedEntityAttribute.getConfidential() != null) {
                    existingTrackedEntityAttribute.setConfidential(trackedEntityAttribute.getConfidential());
                }
                if (trackedEntityAttribute.getDisplayFormName() != null) {
                    existingTrackedEntityAttribute.setDisplayFormName(trackedEntityAttribute.getDisplayFormName());
                }
                if (trackedEntityAttribute.getUniquee() != null) {
                    existingTrackedEntityAttribute.setUniquee(trackedEntityAttribute.getUniquee());
                }
                if (trackedEntityAttribute.getDimensionItemType() != null) {
                    existingTrackedEntityAttribute.setDimensionItemType(trackedEntityAttribute.getDimensionItemType());
                }
                if (trackedEntityAttribute.getAggregationType() != null) {
                    existingTrackedEntityAttribute.setAggregationType(trackedEntityAttribute.getAggregationType());
                }
                if (trackedEntityAttribute.getDisplayInListNoProgram() != null) {
                    existingTrackedEntityAttribute.setDisplayInListNoProgram(trackedEntityAttribute.getDisplayInListNoProgram());
                }
                if (trackedEntityAttribute.getDisplayName() != null) {
                    existingTrackedEntityAttribute.setDisplayName(trackedEntityAttribute.getDisplayName());
                }
                if (trackedEntityAttribute.getPatterne() != null) {
                    existingTrackedEntityAttribute.setPatterne(trackedEntityAttribute.getPatterne());
                }
                if (trackedEntityAttribute.getSkipSynchronization() != null) {
                    existingTrackedEntityAttribute.setSkipSynchronization(trackedEntityAttribute.getSkipSynchronization());
                }
                if (trackedEntityAttribute.getDisplayShortName() != null) {
                    existingTrackedEntityAttribute.setDisplayShortName(trackedEntityAttribute.getDisplayShortName());
                }
                if (trackedEntityAttribute.getPeriodOffset() != null) {
                    existingTrackedEntityAttribute.setPeriodOffset(trackedEntityAttribute.getPeriodOffset());
                }
                if (trackedEntityAttribute.getDisplayOnVisitSchedule() != null) {
                    existingTrackedEntityAttribute.setDisplayOnVisitSchedule(trackedEntityAttribute.getDisplayOnVisitSchedule());
                }
                if (trackedEntityAttribute.getFormName() != null) {
                    existingTrackedEntityAttribute.setFormName(trackedEntityAttribute.getFormName());
                }
                if (trackedEntityAttribute.getOrgunitScope() != null) {
                    existingTrackedEntityAttribute.setOrgunitScope(trackedEntityAttribute.getOrgunitScope());
                }
                if (trackedEntityAttribute.getDimensionItem() != null) {
                    existingTrackedEntityAttribute.setDimensionItem(trackedEntityAttribute.getDimensionItem());
                }
                if (trackedEntityAttribute.getInherit() != null) {
                    existingTrackedEntityAttribute.setInherit(trackedEntityAttribute.getInherit());
                }
                if (trackedEntityAttribute.getOptionSetValue() != null) {
                    existingTrackedEntityAttribute.setOptionSetValue(trackedEntityAttribute.getOptionSetValue());
                }
                if (trackedEntityAttribute.getTrack() != null) {
                    existingTrackedEntityAttribute.setTrack(trackedEntityAttribute.getTrack());
                }

                return existingTrackedEntityAttribute;
            })
            .map(trackedEntityAttributeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrackedEntityAttribute> findAll(Pageable pageable) {
        log.debug("Request to get all TrackedEntityAttributes");
        return trackedEntityAttributeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrackedEntityAttribute> findOne(String id) {
        log.debug("Request to get TrackedEntityAttribute : {}", id);
        return trackedEntityAttributeRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete TrackedEntityAttribute : {}", id);
        trackedEntityAttributeRepository.deleteById(id);
    }
}
