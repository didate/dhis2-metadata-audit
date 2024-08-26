package com.didate.service.impl;

import com.didate.domain.OptionGroup;
import com.didate.repository.OptionGroupRepository;
import com.didate.service.OptionGroupService;
import java.util.Optional;
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
}
