package com.didate.service.impl;

import com.didate.domain.ProgramRuleAction;
import com.didate.repository.ProgramRuleActionRepository;
import com.didate.service.ProgramRuleActionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.ProgramRuleAction}.
 */
@Service
@Transactional
public class ProgramRuleActionServiceImpl implements ProgramRuleActionService {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleActionServiceImpl.class);

    private final ProgramRuleActionRepository programRuleActionRepository;

    public ProgramRuleActionServiceImpl(ProgramRuleActionRepository programRuleActionRepository) {
        this.programRuleActionRepository = programRuleActionRepository;
    }

    @Override
    public ProgramRuleAction save(ProgramRuleAction programRuleAction) {
        log.debug("Request to save ProgramRuleAction : {}", programRuleAction);
        return programRuleActionRepository.save(programRuleAction);
    }

    @Override
    public ProgramRuleAction update(ProgramRuleAction programRuleAction) {
        log.debug("Request to update ProgramRuleAction : {}", programRuleAction);
        return programRuleActionRepository.save(programRuleAction);
    }

    @Override
    public Optional<ProgramRuleAction> partialUpdate(ProgramRuleAction programRuleAction) {
        log.debug("Request to partially update ProgramRuleAction : {}", programRuleAction);

        return programRuleActionRepository
            .findById(programRuleAction.getId())
            .map(existingProgramRuleAction -> {
                if (programRuleAction.getLastUpdated() != null) {
                    existingProgramRuleAction.setLastUpdated(programRuleAction.getLastUpdated());
                }
                if (programRuleAction.getCreated() != null) {
                    existingProgramRuleAction.setCreated(programRuleAction.getCreated());
                }
                if (programRuleAction.getProgramRuleActionType() != null) {
                    existingProgramRuleAction.setProgramRuleActionType(programRuleAction.getProgramRuleActionType());
                }
                if (programRuleAction.getEvaluationTime() != null) {
                    existingProgramRuleAction.setEvaluationTime(programRuleAction.getEvaluationTime());
                }
                if (programRuleAction.getData() != null) {
                    existingProgramRuleAction.setData(programRuleAction.getData());
                }
                if (programRuleAction.getTemplateUid() != null) {
                    existingProgramRuleAction.setTemplateUid(programRuleAction.getTemplateUid());
                }
                if (programRuleAction.getContent() != null) {
                    existingProgramRuleAction.setContent(programRuleAction.getContent());
                }
                if (programRuleAction.getDisplayContent() != null) {
                    existingProgramRuleAction.setDisplayContent(programRuleAction.getDisplayContent());
                }
                if (programRuleAction.getTrack() != null) {
                    existingProgramRuleAction.setTrack(programRuleAction.getTrack());
                }

                return existingProgramRuleAction;
            })
            .map(programRuleActionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramRuleAction> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramRuleActions");
        return programRuleActionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramRuleAction> findOne(String id) {
        log.debug("Request to get ProgramRuleAction : {}", id);
        return programRuleActionRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProgramRuleAction : {}", id);
        programRuleActionRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return programRuleActionRepository.existsById(id);
    }
}
