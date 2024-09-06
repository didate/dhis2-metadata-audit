package com.didate.service.impl;

import com.didate.domain.IndicatorType;
import com.didate.repository.IndicatortypeRepository;
import com.didate.service.IndicatortypeService;
import com.didate.service.dto.IndicatorTypeDTO;
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
 * Service Implementation for managing {@link IndicatorType}.
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
    public IndicatorType save(IndicatorType indicatortype) {
        log.debug("Request to save Indicatortype : {}", indicatortype);
        return indicatortypeRepository.save(indicatortype);
    }

    @Override
    public IndicatorType update(IndicatorType indicatortype) {
        log.debug("Request to update Indicatortype : {}", indicatortype);
        indicatortype.setIsPersisted();
        return indicatortypeRepository.save(indicatortype);
    }

    @Override
    public Optional<IndicatorType> partialUpdate(IndicatorType indicatortype) {
        log.debug("Request to partially update Indicatortype : {}", indicatortype);

        return indicatortypeRepository
            .findById(indicatortype.getId())
            .map(existingIndicatortype -> {
                if (existingIndicatortype.getLastUpdated().equals(indicatortype.getLastUpdated())) {
                    return existingIndicatortype;
                }
                if (indicatortype.getName() != null) {
                    existingIndicatortype.setName(indicatortype.getName());
                }

                if (!indicatortype.getCreatedBy().equals(existingIndicatortype.getCreatedBy())) {
                    existingIndicatortype.setCreatedBy(indicatortype.getCreatedBy());
                }
                if (!indicatortype.getLastUpdatedBy().equals(existingIndicatortype.getLastUpdatedBy())) {
                    existingIndicatortype.setLastUpdatedBy(indicatortype.getLastUpdatedBy());
                }

                return existingIndicatortype;
            })
            .map(indicatortypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IndicatorType> findAll(Pageable pageable) {
        log.debug("Request to get all Indicatortypes");
        return indicatortypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndicatorType> findOne(String id) {
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

    @Override
    public Long count() {
        return indicatortypeRepository.count();
    }

    @Override
    public List<IndicatorTypeDTO> findAudits(String id) {
        return indicatortypeRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                IndicatorType indicatorType = revision.getEntity();
                Hibernate.unproxy(indicatorType.getCreatedBy());
                Hibernate.unproxy(indicatorType.getLastUpdatedBy());
                return new IndicatorTypeDTO(indicatorType).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IndicatorTypeDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        IndicatorType indicatorType = indicatortypeRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(indicatorType.getCreatedBy());
        Hibernate.unproxy(indicatorType.getLastUpdatedBy());

        return new IndicatorTypeDTO(indicatorType);
    }
}
