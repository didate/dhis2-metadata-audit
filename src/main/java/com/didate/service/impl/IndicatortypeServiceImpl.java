package com.didate.service.impl;

import com.didate.domain.Indicatortype;
import com.didate.repository.IndicatortypeRepository;
import com.didate.service.IndicatortypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Indicatortype}.
 */
@Service
@Transactional
public class IndicatortypeServiceImpl implements IndicatortypeService {

    private final Logger log = LoggerFactory.getLogger(IndicatortypeServiceImpl.class);

    private final IndicatortypeRepository indicatortypeRepository;

    public IndicatortypeServiceImpl(IndicatortypeRepository indicatortypeRepository) {
        this.indicatortypeRepository = indicatortypeRepository;
    }

    @Override
    public Indicatortype save(Indicatortype indicatortype) {
        log.debug("Request to save Indicatortype : {}", indicatortype);
        return indicatortypeRepository.save(indicatortype);
    }

    @Override
    public Indicatortype update(Indicatortype indicatortype) {
        log.debug("Request to update Indicatortype : {}", indicatortype);
        indicatortype.setIsPersisted();
        return indicatortypeRepository.save(indicatortype);
    }

    @Override
    public Optional<Indicatortype> partialUpdate(Indicatortype indicatortype) {
        log.debug("Request to partially update Indicatortype : {}", indicatortype);

        return indicatortypeRepository
            .findById(indicatortype.getId())
            .map(existingIndicatortype -> {
                if (indicatortype.getName() != null) {
                    existingIndicatortype.setName(indicatortype.getName());
                }

                return existingIndicatortype;
            })
            .map(indicatortypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Indicatortype> findAll(Pageable pageable) {
        log.debug("Request to get all Indicatortypes");
        return indicatortypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Indicatortype> findOne(String id) {
        log.debug("Request to get Indicatortype : {}", id);
        return indicatortypeRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Indicatortype : {}", id);
        indicatortypeRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return indicatortypeRepository.existsById(id);
    }
}
