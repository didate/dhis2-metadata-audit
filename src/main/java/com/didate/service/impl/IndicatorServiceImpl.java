package com.didate.service.impl;

import com.didate.domain.DataSet;
import com.didate.domain.Indicator;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.IndicatorRepository;
import com.didate.service.IndicatorService;
import com.didate.service.dto.IndicatorDTO;
import com.didate.service.dto.IndicatorFullDTO;
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
 * Service Implementation for managing {@link Indicator}.
 */
@Service
@Transactional
public class IndicatorServiceImpl implements IndicatorService {

    private final Logger log = LoggerFactory.getLogger(IndicatorServiceImpl.class);

    private final IndicatorRepository indicatorRepository;

    private final GenericFilterService<Indicator> filterService;

    public IndicatorServiceImpl(IndicatorRepository indicatorRepository, GenericFilterService<Indicator> filterService) {
        this.indicatorRepository = indicatorRepository;
        this.filterService = filterService;
    }

    @Override
    public Indicator save(Indicator indicator) {
        log.debug("Request to save Indicator : {}", indicator);
        return indicatorRepository.save(indicator);
    }

    @Override
    public Indicator update(Indicator indicator) {
        log.debug("Request to update Indicator : {}", indicator);
        indicator.setIsPersisted();
        return indicatorRepository.save(indicator);
    }

    @Override
    public Optional<Indicator> partialUpdate(Indicator indicator) {
        log.debug("Request to partially update Indicator : {}", indicator);

        return indicatorRepository
            .findById(indicator.getId())
            .map(existingIndicator -> {
                if (existingIndicator.getLastUpdated().equals(indicator.getLastUpdated())) {
                    return existingIndicator;
                }
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

                if (!indicator.getCreatedBy().equals(existingIndicator.getCreatedBy())) {
                    existingIndicator.setCreatedBy(indicator.getCreatedBy());
                }
                if (!indicator.getLastUpdatedBy().equals(existingIndicator.getLastUpdatedBy())) {
                    existingIndicator.setLastUpdatedBy(indicator.getLastUpdatedBy());
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

    @Override
    public Long count() {
        return indicatorRepository.count();
    }

    @Override
    public List<IndicatorDTO> findAudits(String id) {
        Pageable pageable = PageRequest.of(0, 20, RevisionSort.desc());
        return indicatorRepository
            .findRevisions(id, pageable)
            .getContent()
            .stream()
            .map(revision -> {
                Indicator indicator = revision.getEntity();
                Hibernate.unproxy(indicator.getCreatedBy());
                Hibernate.unproxy(indicator.getLastUpdatedBy());
                Hibernate.unproxy(indicator.getIndicatorType());

                return new IndicatorDTO(indicator).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IndicatorFullDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        Indicator indicator = indicatorRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(indicator.getCreatedBy());
        Hibernate.unproxy(indicator.getLastUpdatedBy());
        Hibernate.unproxy(indicator.getIndicatorType());

        return new IndicatorFullDTO(indicator);
    }

    @Override
    public Page<IndicatorDTO> findAllIndicators(Pageable pageable) {
        return indicatorRepository.findAll(pageable).map(IndicatorDTO::new);
    }

    @Override
    public Page<IndicatorDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(indicatorRepository, id, name, pageable).map(IndicatorDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return indicatorRepository.countByTrack(track);
    }
}
