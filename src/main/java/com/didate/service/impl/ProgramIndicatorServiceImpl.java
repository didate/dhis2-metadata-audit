package com.didate.service.impl;

import com.didate.domain.ProgramIndicator;
import com.didate.repository.ProgramIndicatorRepository;
import com.didate.service.ProgramIndicatorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProgramIndicator}.
 */
@Service
@Transactional
public class ProgramIndicatorServiceImpl implements ProgramIndicatorService {

    private final Logger log = LoggerFactory.getLogger(ProgramIndicatorServiceImpl.class);

    private final ProgramIndicatorRepository programIndicatorRepository;

    public ProgramIndicatorServiceImpl(ProgramIndicatorRepository programIndicatorRepository) {
        this.programIndicatorRepository = programIndicatorRepository;
    }

    @Override
    public ProgramIndicator save(ProgramIndicator programIndicator) {
        log.debug("Request to save ProgramIndicator : {}", programIndicator);
        return programIndicatorRepository.save(programIndicator);
    }

    @Override
    public ProgramIndicator update(ProgramIndicator programIndicator) {
        log.debug("Request to update ProgramIndicator : {}", programIndicator);
        programIndicator.setIsPersisted();
        return programIndicatorRepository.save(programIndicator);
    }

    @Override
    public Optional<ProgramIndicator> partialUpdate(ProgramIndicator programIndicator) {
        log.debug("Request to partially update ProgramIndicator : {}", programIndicator);

        return programIndicatorRepository
            .findById(programIndicator.getId())
            .map(existingProgramIndicator -> {
                if (programIndicator.getName() != null) {
                    existingProgramIndicator.setName(programIndicator.getName());
                }
                if (programIndicator.getCreated() != null) {
                    existingProgramIndicator.setCreated(programIndicator.getCreated());
                }
                if (programIndicator.getLastUpdated() != null) {
                    existingProgramIndicator.setLastUpdated(programIndicator.getLastUpdated());
                }
                if (programIndicator.getShortName() != null) {
                    existingProgramIndicator.setShortName(programIndicator.getShortName());
                }
                if (programIndicator.getDimensionItemType() != null) {
                    existingProgramIndicator.setDimensionItemType(programIndicator.getDimensionItemType());
                }
                if (programIndicator.getExpression() != null) {
                    existingProgramIndicator.setExpression(programIndicator.getExpression());
                }
                if (programIndicator.getFilter() != null) {
                    existingProgramIndicator.setFilter(programIndicator.getFilter());
                }
                if (programIndicator.getAnalyticsType() != null) {
                    existingProgramIndicator.setAnalyticsType(programIndicator.getAnalyticsType());
                }
                if (programIndicator.getDimensionItem() != null) {
                    existingProgramIndicator.setDimensionItem(programIndicator.getDimensionItem());
                }
                if (programIndicator.getDisplayShortName() != null) {
                    existingProgramIndicator.setDisplayShortName(programIndicator.getDisplayShortName());
                }
                if (programIndicator.getDisplayName() != null) {
                    existingProgramIndicator.setDisplayName(programIndicator.getDisplayName());
                }
                if (programIndicator.getDisplayFormName() != null) {
                    existingProgramIndicator.setDisplayFormName(programIndicator.getDisplayFormName());
                }
                if (programIndicator.getTrack() != null) {
                    existingProgramIndicator.setTrack(programIndicator.getTrack());
                }

                return existingProgramIndicator;
            })
            .map(programIndicatorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramIndicator> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramIndicators");
        return programIndicatorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramIndicator> findOne(String id) {
        log.debug("Request to get ProgramIndicator : {}", id);
        return programIndicatorRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProgramIndicator : {}", id);
        programIndicatorRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return programIndicatorRepository.existsById(id);
    }

    @Override
    public Long count() {
        return programIndicatorRepository.count();
    }
}
