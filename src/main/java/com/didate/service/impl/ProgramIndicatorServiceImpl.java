package com.didate.service.impl;

import com.didate.domain.OrganisationUnit;
import com.didate.domain.ProgramIndicator;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramIndicatorRepository;
import com.didate.service.ProgramIndicatorService;
import com.didate.service.dto.ProgramIndicatorDTO;
import com.didate.service.dto.ProgramIndicatorFullDTO;
import com.didate.service.search.GenericFilterService;
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
 * Service Implementation for managing {@link ProgramIndicator}.
 */
@Service
@Transactional
public class ProgramIndicatorServiceImpl implements ProgramIndicatorService {

    private final Logger log = LoggerFactory.getLogger(ProgramIndicatorServiceImpl.class);

    private final ProgramIndicatorRepository programIndicatorRepository;
    private final GenericFilterService<ProgramIndicator> filterService;

    public ProgramIndicatorServiceImpl(
        ProgramIndicatorRepository programIndicatorRepository,
        GenericFilterService<ProgramIndicator> filterService
    ) {
        this.programIndicatorRepository = programIndicatorRepository;
        this.filterService = filterService;
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
                if (existingProgramIndicator.getLastUpdated().equals(programIndicator.getLastUpdated())) {
                    return existingProgramIndicator;
                }
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

                if (!programIndicator.getCreatedBy().equals(existingProgramIndicator.getCreatedBy())) {
                    existingProgramIndicator.setCreatedBy(programIndicator.getCreatedBy());
                }
                if (!programIndicator.getLastUpdatedBy().equals(existingProgramIndicator.getLastUpdatedBy())) {
                    existingProgramIndicator.setLastUpdatedBy(programIndicator.getLastUpdatedBy());
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

    @Override
    public List<ProgramIndicatorDTO> findAudits(String id) {
        return programIndicatorRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                ProgramIndicator programIndicator = revision.getEntity();
                Hibernate.unproxy(programIndicator.getCreatedBy());
                Hibernate.unproxy(programIndicator.getLastUpdatedBy());
                Hibernate.unproxy(programIndicator.getProgram());
                return new ProgramIndicatorDTO(programIndicator).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramIndicatorFullDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        ProgramIndicator programIndicator = programIndicatorRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(programIndicator.getCreatedBy());
        Hibernate.unproxy(programIndicator.getLastUpdatedBy());
        Hibernate.unproxy(programIndicator.getProgram());

        return new ProgramIndicatorFullDTO(programIndicator);
    }

    @Override
    public Page<ProgramIndicatorDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(programIndicatorRepository, id, name, pageable).map(ProgramIndicatorDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return programIndicatorRepository.countByTrack(track);
    }
}
