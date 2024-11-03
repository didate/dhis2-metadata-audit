package com.didate.service.impl;

import com.didate.domain.IndicatorType;
import com.didate.domain.OptionGroup;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.OptionGroupRepository;
import com.didate.service.OptionGroupService;
import com.didate.service.dto.OptionGroupDTO;
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
 * Service Implementation for managing {@link OptionGroup}.
 */
@Service
@Transactional
public class OptionGroupServiceImpl implements OptionGroupService {

    private final Logger log = LoggerFactory.getLogger(OptionGroupServiceImpl.class);

    private final OptionGroupRepository optionGroupRepository;
    private final GenericFilterService<OptionGroup> filterService;

    public OptionGroupServiceImpl(OptionGroupRepository optionGroupRepository, GenericFilterService<OptionGroup> filterService) {
        this.optionGroupRepository = optionGroupRepository;
        this.filterService = filterService;
    }

    @Override
    public OptionGroup save(OptionGroup optionGroup) {
        log.debug("Request to save OptionGroup : {}", optionGroup);
        return optionGroupRepository.save(optionGroup);
    }

    @Override
    public OptionGroup update(OptionGroup optionGroup) {
        log.debug("Request to update OptionGroup : {}", optionGroup);
        optionGroup.setIsPersisted();
        // no save call needed as we have no fields that can be updated
        return optionGroup;
    }

    @Override
    public Optional<OptionGroup> partialUpdate(OptionGroup optionGroup) {
        log.debug("Request to partially update OptionGroup : {}", optionGroup);

        return optionGroupRepository
            .findById(optionGroup.getId())
            .map(existingOptionGroup -> {
                if (!optionGroup.getName().equals(existingOptionGroup.getName())) {
                    existingOptionGroup.setName(optionGroup.getName());
                }
                if (!optionGroup.getShortName().equals(existingOptionGroup.getShortName())) {
                    existingOptionGroup.setShortName(optionGroup.getShortName());
                }

                if (optionGroup.getCreated() != null) {
                    existingOptionGroup.setCreated(optionGroup.getCreated());
                }
                if (optionGroup.getLastUpdated() != null) {
                    existingOptionGroup.setLastUpdated(optionGroup.getLastUpdated());
                }

                if (!optionGroup.getCreatedBy().equals(existingOptionGroup.getCreatedBy())) {
                    existingOptionGroup.setCreatedBy(optionGroup.getCreatedBy());
                }
                if (!optionGroup.getLastUpdatedBy().equals(existingOptionGroup.getLastUpdatedBy())) {
                    existingOptionGroup.setLastUpdatedBy(optionGroup.getLastUpdatedBy());
                }

                return existingOptionGroup;
            })
            .map(optionGroupRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptionGroup> findAll(Pageable pageable) {
        log.debug("Request to get all OptionGroups");
        return optionGroupRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OptionGroup> findOne(String id) {
        log.debug("Request to get OptionGroup : {}", id);
        return optionGroupRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OptionGroup : {}", id);
        optionGroupRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return optionGroupRepository.existsById(id);
    }

    @Override
    public Long count() {
        return optionGroupRepository.count();
    }

    @Override
    public List<OptionGroupDTO> findAudits(String id) {
        Pageable pageable = PageRequest.of(0, 20, RevisionSort.desc());
        return optionGroupRepository
            .findRevisions(id, pageable)
            .getContent()
            .stream()
            .map(revision -> {
                OptionGroup optionGroup = revision.getEntity();
                Hibernate.unproxy(optionGroup.getCreatedBy());
                Hibernate.unproxy(optionGroup.getLastUpdatedBy());
                return new OptionGroupDTO(optionGroup).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OptionGroupDTO findAuditRevision(String id, Integer rev) {
        OptionGroup optionGroup = optionGroupRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(optionGroup.getCreatedBy());
        Hibernate.unproxy(optionGroup.getLastUpdatedBy());

        return new OptionGroupDTO(optionGroup);
    }

    @Override
    public Page<OptionGroupDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(optionGroupRepository, id, name, pageable).map(OptionGroupDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return optionGroupRepository.countByTrack(track);
    }

    @Override
    public void setTrackNone() {
        optionGroupRepository.setTrackNone();
    }
}
