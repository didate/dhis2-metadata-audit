package com.didate.service.impl;

import com.didate.domain.OptionGroup;
import com.didate.domain.Program;
import com.didate.repository.OptionGroupRepository;
import com.didate.service.OptionGroupService;
import com.didate.service.dto.OptionGroupDTO;
import com.didate.service.dto.ProgramDTO;
import com.didate.service.dto.ProgramFullDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public OptionGroupServiceImpl(OptionGroupRepository optionGroupRepository) {
        this.optionGroupRepository = optionGroupRepository;
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
                return existingOptionGroup;
            }); // .map(optionGroupRepository::save)
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
        return optionGroupRepository
            .findRevisions(id)
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
}
