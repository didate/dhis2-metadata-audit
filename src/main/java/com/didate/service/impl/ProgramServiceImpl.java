package com.didate.service.impl;

import com.didate.domain.Program;
import com.didate.repository.ProgramRepository;
import com.didate.service.ProgramService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.Program}.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private static final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public Program save(Program program) {
        log.debug("Request to save Program : {}", program);
        return programRepository.save(program);
    }

    @Override
    public Program update(Program program) {
        log.debug("Request to update Program : {}", program);
        return programRepository.save(program);
    }

    @Override
    public Optional<Program> partialUpdate(Program program) {
        log.debug("Request to partially update Program : {}", program);

        return programRepository
            .findById(program.getId())
            .map(existingProgram -> {
                if (program.getName() != null) {
                    existingProgram.setName(program.getName());
                }
                if (program.getCreated() != null) {
                    existingProgram.setCreated(program.getCreated());
                }
                if (program.getLastUpdated() != null) {
                    existingProgram.setLastUpdated(program.getLastUpdated());
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
                if (program.getUserRoles() != null) {
                    existingProgram.setUserRoles(program.getUserRoles());
                }
                if (program.getProgramIndicators() != null) {
                    existingProgram.setProgramIndicators(program.getProgramIndicators());
                }
                if (program.getProgramRuleVariables() != null) {
                    existingProgram.setProgramRuleVariables(program.getProgramRuleVariables());
                }
                if (program.getOnlyEnrollOnce() != null) {
                    existingProgram.setOnlyEnrollOnce(program.getOnlyEnrollOnce());
                }
                if (program.getNotificationTemplates() != null) {
                    existingProgram.setNotificationTemplates(program.getNotificationTemplates());
                }
                if (program.getSelectEnrollmentDatesInFuture() != null) {
                    existingProgram.setSelectEnrollmentDatesInFuture(program.getSelectEnrollmentDatesInFuture());
                }
                if (program.getSelectIncidentDatesInFuture() != null) {
                    existingProgram.setSelectIncidentDatesInFuture(program.getSelectIncidentDatesInFuture());
                }
                if (program.getTrackedEntityType() != null) {
                    existingProgram.setTrackedEntityType(program.getTrackedEntityType());
                }
                if (program.getStyle() != null) {
                    existingProgram.setStyle(program.getStyle());
                }
                if (program.getCategoryCombo() != null) {
                    existingProgram.setCategoryCombo(program.getCategoryCombo());
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
                if (program.getAttributeValuesCount() != null) {
                    existingProgram.setAttributeValuesCount(program.getAttributeValuesCount());
                }
                if (program.getOrganisationUnitsCount() != null) {
                    existingProgram.setOrganisationUnitsCount(program.getOrganisationUnitsCount());
                }
                if (program.getProgramStagesCount() != null) {
                    existingProgram.setProgramStagesCount(program.getProgramStagesCount());
                }
                if (program.getProgramSectionsCount() != null) {
                    existingProgram.setProgramSectionsCount(program.getProgramSectionsCount());
                }
                if (program.getProgramTrackedEntityAttributesCount() != null) {
                    existingProgram.setProgramTrackedEntityAttributesCount(program.getProgramTrackedEntityAttributesCount());
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

    @Override
    @Transactional(readOnly = true)
    public Optional<Program> findOne(String id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findById(id);
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
}
