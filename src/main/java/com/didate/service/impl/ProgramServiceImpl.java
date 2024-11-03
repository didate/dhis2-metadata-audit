package com.didate.service.impl;

import com.didate.domain.Program;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramRepository;
import com.didate.service.ProgramService;
import com.didate.service.dto.ProgramDTO;
import com.didate.service.dto.ProgramFullDTO;
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
 * Service Implementation for managing {@link Program}.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;
    private final GenericFilterService<Program> filterService;

    public ProgramServiceImpl(ProgramRepository programRepository, GenericFilterService<Program> filterService) {
        this.programRepository = programRepository;
        this.filterService = filterService;
    }

    @Override
    public Program save(Program program) {
        log.debug("Request to save Program : {}", program);
        return programRepository.save(program);
    }

    @Override
    public Program update(Program program) {
        log.debug("Request to update Program : {}", program);
        program.setIsPersisted();
        return programRepository.save(program);
    }

    @Override
    public Optional<Program> partialUpdate(Program program) {
        log.debug("Request to partially update Program : {}", program);

        return programRepository
            .findById(program.getId())
            .map(existingProgram -> {
                if (existingProgram.getLastUpdated().equals(program.getLastUpdated())) {
                    return existingProgram;
                }
                if (program.getName() != null) {
                    existingProgram.setName(program.getName());
                }
                if (program.getCreated() != null) {
                    existingProgram.setCreated(program.getCreated());
                }
                if (program.getLastUpdated() != null) {
                    existingProgram.setLastUpdated(program.getLastUpdated());
                }
                if (program.getCreatedBy() != null) {
                    existingProgram.setCreatedBy(program.getCreatedBy());
                }
                if (program.getLastUpdatedBy() != null) {
                    existingProgram.setLastUpdatedBy(program.getLastUpdatedBy());
                }
                if (program.getCategoryCombo() != null) {
                    existingProgram.setCategoryCombo(program.getCategoryCombo());
                }
                if (program.getShortName() != null) {
                    existingProgram.setShortName(program.getShortName());
                }
                if (program.getDescription() != null) {
                    existingProgram.setDescription(program.getDescription());
                }
                if (program.getVersion() != null) {
                    existingProgram.setVersion(program.getVersion());
                }
                if (program.getEnrollmentDateLabel() != null) {
                    existingProgram.setEnrollmentDateLabel(program.getEnrollmentDateLabel());
                }
                if (program.getIncidentDateLabel() != null) {
                    existingProgram.setIncidentDateLabel(program.getIncidentDateLabel());
                }
                if (program.getProgramType() != null) {
                    existingProgram.setProgramType(program.getProgramType());
                }
                if (program.getDisplayIncidentDate() != null) {
                    existingProgram.setDisplayIncidentDate(program.getDisplayIncidentDate());
                }
                if (program.getIgnoreOverdueEvents() != null) {
                    existingProgram.setIgnoreOverdueEvents(program.getIgnoreOverdueEvents());
                }

                if (program.getOnlyEnrollOnce() != null) {
                    existingProgram.setOnlyEnrollOnce(program.getOnlyEnrollOnce());
                }

                if (program.getSelectEnrollmentDatesInFuture() != null) {
                    existingProgram.setSelectEnrollmentDatesInFuture(program.getSelectEnrollmentDatesInFuture());
                }
                if (program.getSelectIncidentDatesInFuture() != null) {
                    existingProgram.setSelectIncidentDatesInFuture(program.getSelectIncidentDatesInFuture());
                }

                if (program.getSkipOffline() != null) {
                    existingProgram.setSkipOffline(program.getSkipOffline());
                }
                if (program.getDisplayFrontPageList() != null) {
                    existingProgram.setDisplayFrontPageList(program.getDisplayFrontPageList());
                }
                if (program.getUseFirstStageDuringRegistration() != null) {
                    existingProgram.setUseFirstStageDuringRegistration(program.getUseFirstStageDuringRegistration());
                }
                if (program.getExpiryDays() != null) {
                    existingProgram.setExpiryDays(program.getExpiryDays());
                }
                if (program.getCompleteEventsExpiryDays() != null) {
                    existingProgram.setCompleteEventsExpiryDays(program.getCompleteEventsExpiryDays());
                }
                if (program.getOpenDaysAfterCoEndDate() != null) {
                    existingProgram.setOpenDaysAfterCoEndDate(program.getOpenDaysAfterCoEndDate());
                }
                if (program.getMinAttributesRequiredToSearch() != null) {
                    existingProgram.setMinAttributesRequiredToSearch(program.getMinAttributesRequiredToSearch());
                }
                if (program.getMaxTeiCountToReturn() != null) {
                    existingProgram.setMaxTeiCountToReturn(program.getMaxTeiCountToReturn());
                }
                if (program.getAccessLevel() != null) {
                    existingProgram.setAccessLevel(program.getAccessLevel());
                }
                if (program.getDisplayEnrollmentDateLabel() != null) {
                    existingProgram.setDisplayEnrollmentDateLabel(program.getDisplayEnrollmentDateLabel());
                }
                if (program.getDisplayIncidentDateLabel() != null) {
                    existingProgram.setDisplayIncidentDateLabel(program.getDisplayIncidentDateLabel());
                }
                if (program.getRegistration() != null) {
                    existingProgram.setRegistration(program.getRegistration());
                }
                if (program.getWithoutRegistration() != null) {
                    existingProgram.setWithoutRegistration(program.getWithoutRegistration());
                }
                if (program.getDisplayShortName() != null) {
                    existingProgram.setDisplayShortName(program.getDisplayShortName());
                }
                if (program.getDisplayDescription() != null) {
                    existingProgram.setDisplayDescription(program.getDisplayDescription());
                }
                if (program.getDisplayFormName() != null) {
                    existingProgram.setDisplayFormName(program.getDisplayFormName());
                }
                if (program.getDisplayName() != null) {
                    existingProgram.setDisplayName(program.getDisplayName());
                }
                if (program.getOrganisationUnitsCount() != null) {
                    existingProgram.setOrganisationUnitsCount(program.getOrganisationUnitsCount());
                }
                if (program.getProgramStagesCount() != null) {
                    existingProgram.setProgramStagesCount(program.getProgramStagesCount());
                }
                if (program.getProgramIndicatorsCount() != null) {
                    existingProgram.setProgramIndicatorsCount(program.getProgramIndicatorsCount());
                }
                if (program.getProgramTrackedEntityAttributesCount() != null) {
                    existingProgram.setProgramTrackedEntityAttributesCount(program.getProgramTrackedEntityAttributesCount());
                }
                if (program.getOrganisationUnitsContent() != null) {
                    existingProgram.setOrganisationUnitsContent(program.getOrganisationUnitsContent());
                }
                if (program.getProgramStagesContent() != null) {
                    existingProgram.setProgramStagesContent(program.getProgramStagesContent());
                }
                if (program.getProgramIndicatorsContent() != null) {
                    existingProgram.setProgramIndicatorsContent(program.getProgramIndicatorsContent());
                }
                if (program.getProgramTrackedEntityAttributesContent() != null) {
                    existingProgram.setProgramTrackedEntityAttributesContent(program.getProgramTrackedEntityAttributesContent());
                }
                if (program.getTrack() != null) {
                    existingProgram.setTrack(program.getTrack());
                }

                if (!program.getCreatedBy().equals(existingProgram.getCreatedBy())) {
                    existingProgram.setCreatedBy(program.getCreatedBy());
                }
                if (!program.getLastUpdatedBy().equals(existingProgram.getLastUpdatedBy())) {
                    existingProgram.setLastUpdatedBy(program.getLastUpdatedBy());
                }

                return existingProgram;
            })
            .map(programRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Program> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable);
    }

    public Page<Program> findAllWithEagerRelationships(Pageable pageable) {
        return programRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Program> findOne(String id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return programRepository.existsById(id);
    }

    @Override
    public Long count() {
        return programRepository.count();
    }

    @Override
    public Page<ProgramDTO> findAllPrograms(Pageable pageable) {
        return programRepository.findAll(pageable).map(ProgramDTO::new);
    }

    @Override
    public List<ProgramDTO> findAudits(String id) {
        Pageable pageable = PageRequest.of(0, 20, RevisionSort.desc());
        return programRepository
            .findRevisions(id, pageable)
            .getContent()
            .stream()
            .map(revision -> {
                Program p = revision.getEntity();
                Hibernate.unproxy(p.getCreatedBy());
                Hibernate.unproxy(p.getLastUpdatedBy());
                return new ProgramDTO(p).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramFullDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        Program program = programRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(program.getCreatedBy());
        Hibernate.unproxy(program.getLastUpdatedBy());
        Hibernate.unproxy(program.getCategoryCombo());

        return new ProgramFullDTO(program);
    }

    @Override
    public Page<ProgramDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(programRepository, id, name, pageable).map(ProgramDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return programRepository.countByTrack(track);
    }
}
