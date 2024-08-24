package com.didate.service.impl;

import com.didate.domain.OptionSet;
import com.didate.repository.OptionsetRepository;
import com.didate.service.OptionsetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OptionSet}.
 */
@Service
@Transactional
public class OptionsetServiceImpl implements OptionsetService {

    private final Logger log = LoggerFactory.getLogger(OptionsetServiceImpl.class);

    private final OptionsetRepository optionsetRepository;

    public OptionsetServiceImpl(OptionsetRepository optionsetRepository) {
        this.optionsetRepository = optionsetRepository;
    }

    @Override
    public OptionSet save(OptionSet optionset) {
        log.debug("Request to save OptionSet : {}", optionset);
        return optionsetRepository.save(optionset);
    }

    @Override
    public OptionSet update(OptionSet optionset) {
        log.debug("Request to update OptionSet : {}", optionset);
        optionset.setIsPersisted();
        return optionsetRepository.save(optionset);
    }

    @Override
    public Optional<OptionSet> partialUpdate(OptionSet optionset) {
        log.debug("Request to partially update OptionSet : {}", optionset);

        return optionsetRepository
            .findById(optionset.getId())
            .map(existingOptionset -> {
                if (optionset.getName() != null) {
                    existingOptionset.setName(optionset.getName());
                }

                return existingOptionset;
            })
            .map(optionsetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptionSet> findAll(Pageable pageable) {
        log.debug("Request to get all Optionsets");
        return optionsetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OptionSet> findOne(String id) {
        log.debug("Request to get OptionSet : {}", id);
        return optionsetRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OptionSet : {}", id);
        optionsetRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return optionsetRepository.existsById(id);
    }
}
