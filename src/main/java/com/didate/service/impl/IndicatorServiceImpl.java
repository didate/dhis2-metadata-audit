package com.didate.service.impl;

import com.didate.domain.Indicator;
import com.didate.repository.IndicatorRepository;
import com.didate.service.IndicatorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.Indicator}.
 */
@Service
@Transactional
public class IndicatorServiceImpl implements IndicatorService {

    private static final Logger log = LoggerFactory.getLogger(IndicatorServiceImpl.class);

    private final IndicatorRepository indicatorRepository;

    public IndicatorServiceImpl(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    @Override
    public Indicator save(Indicator indicator) {
        log.debug("Request to save Indicator : {}", indicator);
        return indicatorRepository.save(indicator);
    }

    @Override
    public Indicator update(Indicator indicator) {
        log.debug("Request to update Indicator : {}", indicator);
        return indicatorRepository.save(indicator);
    }

    @Override
    public Optional<Indicator> partialUpdate(Indicator indicator) {
        log.debug("Request to partially update Indicator : {}", indicator);

        return indicatorRepository
            .findById(indicator.getId())
            .map(existingIndicator -> {
                if (indicator.getName() != null) {
                    existingIndicator.setName(indicator.getName());
                }
                if (indicator.getShortName() != null) {
                    existingIndicator.setShortName(indicator.getShortName());
                }
                if (indicator.getDisplayShortName() != null) {
                    existingIndicator.setDisplayShortName(indicator.getDisplayShortName());
                }
                if (indicator.getDisplayName() != null) {
                    existingIndicator.setDisplayName(indicator.getDisplayName());
                }
                if (indicator.getDisplayFormName() != null) {
                    existingIndicator.setDisplayFormName(indicator.getDisplayFormName());
                }
                if (indicator.getCreated() != null) {
                    existingIndicator.setCreated(indicator.getCreated());
                }
                if (indicator.getLastUpdated() != null) {
                    existingIndicator.setLastUpdated(indicator.getLastUpdated());
                }
                if (indicator.getPublicAccess() != null) {
                    existingIndicator.setPublicAccess(indicator.getPublicAccess());
                }
                if (indicator.getDimensionItemType() != null) {
                    existingIndicator.setDimensionItemType(indicator.getDimensionItemType());
                }
                if (indicator.getAnnualized() != null) {
                    existingIndicator.setAnnualized(indicator.getAnnualized());
                }
                if (indicator.getNumerator() != null) {
                    existingIndicator.setNumerator(indicator.getNumerator());
                }
                if (indicator.getNumeratorDescription() != null) {
                    existingIndicator.setNumeratorDescription(indicator.getNumeratorDescription());
                }
                if (indicator.getDenominator() != null) {
                    existingIndicator.setDenominator(indicator.getDenominator());
                }
                if (indicator.getDenominatorDescription() != null) {
                    existingIndicator.setDenominatorDescription(indicator.getDenominatorDescription());
                }
                if (indicator.getDisplayNumeratorDescription() != null) {
                    existingIndicator.setDisplayNumeratorDescription(indicator.getDisplayNumeratorDescription());
                }
                if (indicator.getDisplayDenominatorDescription() != null) {
                    existingIndicator.setDisplayDenominatorDescription(indicator.getDisplayDenominatorDescription());
                }
                if (indicator.getDimensionItem() != null) {
                    existingIndicator.setDimensionItem(indicator.getDimensionItem());
                }
                if (indicator.getTrack() != null) {
                    existingIndicator.setTrack(indicator.getTrack());
                }

                return existingIndicator;
            })
            .map(indicatorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Indicator> findAll(Pageable pageable) {
        log.debug("Request to get all Indicators");
        return indicatorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Indicator> findOne(String id) {
        log.debug("Request to get Indicator : {}", id);
        return indicatorRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Indicator : {}", id);
        indicatorRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return indicatorRepository.existsById(id);
    }
}
