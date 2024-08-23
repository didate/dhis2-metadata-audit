package com.didate.service.impl;

import com.didate.domain.ProgramStage;
import com.didate.repository.ProgramStageRepository;
import com.didate.service.ProgramStageService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProgramStage}.
 */
@Service
@Transactional
public class ProgramStageServiceImpl implements ProgramStageService {

    private final Logger log = LoggerFactory.getLogger(ProgramStageServiceImpl.class);

    private final ProgramStageRepository programStageRepository;

    public ProgramStageServiceImpl(ProgramStageRepository programStageRepository) {
        this.programStageRepository = programStageRepository;
    }

    @Override
    public ProgramStage save(ProgramStage programStage) {
        log.debug("Request to save ProgramStage : {}", programStage);
        return programStageRepository.save(programStage);
    }

    @Override
    public ProgramStage update(ProgramStage programStage) {
        log.debug("Request to update ProgramStage : {}", programStage);
        programStage.setIsPersisted();
        return programStageRepository.save(programStage);
    }

    @Override
    public Optional<ProgramStage> partialUpdate(ProgramStage programStage) {
        log.debug("Request to partially update ProgramStage : {}", programStage);

        return programStageRepository
            .findById(programStage.getId())
            .map(existingProgramStage -> {
                if (programStage.getName() != null) {
                    existingProgramStage.setName(programStage.getName());
                }
                if (programStage.getCreated() != null) {
                    existingProgramStage.setCreated(programStage.getCreated());
                }
                if (programStage.getLastUpdated() != null) {
                    existingProgramStage.setLastUpdated(programStage.getLastUpdated());
                }
                if (programStage.getMinDaysFromStart() != null) {
                    existingProgramStage.setMinDaysFromStart(programStage.getMinDaysFromStart());
                }
                if (programStage.getExecutionDateLabel() != null) {
                    existingProgramStage.setExecutionDateLabel(programStage.getExecutionDateLabel());
                }
                if (programStage.getAutoGenerateEvent() != null) {
                    existingProgramStage.setAutoGenerateEvent(programStage.getAutoGenerateEvent());
                }
                if (programStage.getValidationStrategy() != null) {
                    existingProgramStage.setValidationStrategy(programStage.getValidationStrategy());
                }
                if (programStage.getDisplayGenerateEventBox() != null) {
                    existingProgramStage.setDisplayGenerateEventBox(programStage.getDisplayGenerateEventBox());
                }
                if (programStage.getFeatureType() != null) {
                    existingProgramStage.setFeatureType(programStage.getFeatureType());
                }
                if (programStage.getBlockEntryForm() != null) {
                    existingProgramStage.setBlockEntryForm(programStage.getBlockEntryForm());
                }
                if (programStage.getPreGenerateUID() != null) {
                    existingProgramStage.setPreGenerateUID(programStage.getPreGenerateUID());
                }
                if (programStage.getRemindCompleted() != null) {
                    existingProgramStage.setRemindCompleted(programStage.getRemindCompleted());
                }
                if (programStage.getGeneratedByEnrollmentDate() != null) {
                    existingProgramStage.setGeneratedByEnrollmentDate(programStage.getGeneratedByEnrollmentDate());
                }
                if (programStage.getAllowGenerateNextVisit() != null) {
                    existingProgramStage.setAllowGenerateNextVisit(programStage.getAllowGenerateNextVisit());
                }
                if (programStage.getOpenAfterEnrollment() != null) {
                    existingProgramStage.setOpenAfterEnrollment(programStage.getOpenAfterEnrollment());
                }
                if (programStage.getSortOrder() != null) {
                    existingProgramStage.setSortOrder(programStage.getSortOrder());
                }
                if (programStage.getHideDueDate() != null) {
                    existingProgramStage.setHideDueDate(programStage.getHideDueDate());
                }
                if (programStage.getEnableUserAssignment() != null) {
                    existingProgramStage.setEnableUserAssignment(programStage.getEnableUserAssignment());
                }
                if (programStage.getReferral() != null) {
                    existingProgramStage.setReferral(programStage.getReferral());
                }
                if (programStage.getDisplayExecutionDateLabel() != null) {
                    existingProgramStage.setDisplayExecutionDateLabel(programStage.getDisplayExecutionDateLabel());
                }
                if (programStage.getFormType() != null) {
                    existingProgramStage.setFormType(programStage.getFormType());
                }
                if (programStage.getDisplayFormName() != null) {
                    existingProgramStage.setDisplayFormName(programStage.getDisplayFormName());
                }
                if (programStage.getDisplayName() != null) {
                    existingProgramStage.setDisplayName(programStage.getDisplayName());
                }
                if (programStage.getRepeatable() != null) {
                    existingProgramStage.setRepeatable(programStage.getRepeatable());
                }
                if (programStage.getProgramStageDataElementsCount() != null) {
                    existingProgramStage.setProgramStageDataElementsCount(programStage.getProgramStageDataElementsCount());
                }
                if (programStage.getProgramStageDataElementsContent() != null) {
                    existingProgramStage.setProgramStageDataElementsContent(programStage.getProgramStageDataElementsContent());
                }

                return existingProgramStage;
            })
            .map(programStageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramStage> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramStages");
        return programStageRepository.findAll(pageable);
    }

    public Page<ProgramStage> findAllWithEagerRelationships(Pageable pageable) {
        return programStageRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramStage> findOne(String id) {
        log.debug("Request to get ProgramStage : {}", id);
        return programStageRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProgramStage : {}", id);
        programStageRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return programStageRepository.existsById(id);
    }
}
