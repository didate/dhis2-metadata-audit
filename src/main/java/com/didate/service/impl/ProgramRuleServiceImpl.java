package com.didate.service.impl;

import com.didate.domain.ProgramRule;
import com.didate.repository.ProgramRuleRepository;
import com.didate.service.ProgramRuleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProgramRule}.
 */
@Service
@Transactional
public class ProgramRuleServiceImpl implements ProgramRuleService {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleServiceImpl.class);

    private final ProgramRuleRepository programRuleRepository;

    public ProgramRuleServiceImpl(ProgramRuleRepository programRuleRepository) {
        this.programRuleRepository = programRuleRepository;
    }

    @Override
    public ProgramRule save(ProgramRule programRule) {
        log.debug("Request to save ProgramRule : {}", programRule);
        return programRuleRepository.save(programRule);
    }

    @Override
    public ProgramRule update(ProgramRule programRule) {
        log.debug("Request to update ProgramRule : {}", programRule);
        programRule.setIsPersisted();
        return programRuleRepository.save(programRule);
    }

    @Override
    public Optional<ProgramRule> partialUpdate(ProgramRule programRule) {
        log.debug("Request to partially update ProgramRule : {}", programRule);

        return programRuleRepository
            .findById(programRule.getId())
            .map(existingProgramRule -> {
                if (programRule.getLastUpdated() != null) {
                    existingProgramRule.setLastUpdated(programRule.getLastUpdated());
                }
                if (programRule.getCreated() != null) {
                    existingProgramRule.setCreated(programRule.getCreated());
                }
                if (programRule.getName() != null) {
                    existingProgramRule.setName(programRule.getName());
                }
                if (programRule.getDisplayName() != null) {
                    existingProgramRule.setDisplayName(programRule.getDisplayName());
                }
                if (programRule.getPriority() != null) {
                    existingProgramRule.setPriority(programRule.getPriority());
                }
                if (programRule.getCondition() != null) {
                    existingProgramRule.setCondition(programRule.getCondition());
                }
                if (programRule.getTrack() != null) {
                    existingProgramRule.setTrack(programRule.getTrack());
                }

                return existingProgramRule;
            })
            .map(programRuleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramRule> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramRules");
        return programRuleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramRule> findOne(String id) {
        log.debug("Request to get ProgramRule : {}", id);
        return programRuleRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProgramRule : {}", id);
        programRuleRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return programRuleRepository.existsById(id);
    }

    @Override
    public Long count() {
        return programRuleRepository.count();
    }
}
