package com.didate.service.impl;

import com.didate.domain.Optionset;
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
 * Service Implementation for managing {@link com.didate.domain.Optionset}.
 */
@Service
@Transactional
public class OptionsetServiceImpl implements OptionsetService {

    private static final Logger log = LoggerFactory.getLogger(OptionsetServiceImpl.class);

    private final OptionsetRepository optionsetRepository;

    public OptionsetServiceImpl(OptionsetRepository optionsetRepository) {
        this.optionsetRepository = optionsetRepository;
    }

    @Override
    public Optionset save(Optionset optionset) {
        log.debug("Request to save Optionset : {}", optionset);
        return optionsetRepository.save(optionset);
    }

    @Override
    public Optionset update(Optionset optionset) {
        log.debug("Request to update Optionset : {}", optionset);
        return optionsetRepository.save(optionset);
    }

    @Override
    public Optional<Optionset> partialUpdate(Optionset optionset) {
        log.debug("Request to partially update Optionset : {}", optionset);

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
    public Page<Optionset> findAll(Pageable pageable) {
        log.debug("Request to get all Optionsets");
        return optionsetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Optionset> findOne(String id) {
        log.debug("Request to get Optionset : {}", id);
        return optionsetRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Optionset : {}", id);
        optionsetRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return optionsetRepository.existsById(id);
    }
}
