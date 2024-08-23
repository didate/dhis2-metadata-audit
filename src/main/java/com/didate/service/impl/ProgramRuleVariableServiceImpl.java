package com.didate.service.impl;

import com.didate.domain.ProgramRuleVariable;
import com.didate.repository.ProgramRuleVariableRepository;
import com.didate.service.ProgramRuleVariableService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.ProgramRuleVariable}.
 */
@Service
@Transactional
public class ProgramRuleVariableServiceImpl implements ProgramRuleVariableService {

    private static final Logger log = LoggerFactory.getLogger(ProgramRuleVariableServiceImpl.class);

    private final ProgramRuleVariableRepository programRuleVariableRepository;

    public ProgramRuleVariableServiceImpl(ProgramRuleVariableRepository programRuleVariableRepository) {
        this.programRuleVariableRepository = programRuleVariableRepository;
    }

    @Override
    public ProgramRuleVariable save(ProgramRuleVariable programRuleVariable) {
        log.debug("Request to save ProgramRuleVariable : {}", programRuleVariable);
        return programRuleVariableRepository.save(programRuleVariable);
    }

    @Override
    public ProgramRuleVariable update(ProgramRuleVariable programRuleVariable) {
        log.debug("Request to update ProgramRuleVariable : {}", programRuleVariable);
        return programRuleVariableRepository.save(programRuleVariable);
    }

    @Override
    public Optional<ProgramRuleVariable> partialUpdate(ProgramRuleVariable programRuleVariable) {
        log.debug("Request to partially update ProgramRuleVariable : {}", programRuleVariable);

        return programRuleVariableRepository
            .findById(programRuleVariable.getId())
            .map(existingProgramRuleVariable -> {
                if (programRuleVariable.getLastUpdated() != null) {
                    existingProgramRuleVariable.setLastUpdated(programRuleVariable.getLastUpdated());
                }
                if (programRuleVariable.getCreated() != null) {
                    existingProgramRuleVariable.setCreated(programRuleVariable.getCreated());
                }
                if (programRuleVariable.getName() != null) {
                    existingProgramRuleVariable.setName(programRuleVariable.getName());
                }
                if (programRuleVariable.getDisplayName() != null) {
                    existingProgramRuleVariable.setDisplayName(programRuleVariable.getDisplayName());
                }
                if (programRuleVariable.getProgramRuleVariableSourceType() != null) {
                    existingProgramRuleVariable.setProgramRuleVariableSourceType(programRuleVariable.getProgramRuleVariableSourceType());
                }
                if (programRuleVariable.getUseCodeForOptionSet() != null) {
                    existingProgramRuleVariable.setUseCodeForOptionSet(programRuleVariable.getUseCodeForOptionSet());
                }
                if (programRuleVariable.getTrack() != null) {
                    existingProgramRuleVariable.setTrack(programRuleVariable.getTrack());
                }

                return existingProgramRuleVariable;
            })
            .map(programRuleVariableRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramRuleVariable> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramRuleVariables");
        return programRuleVariableRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramRuleVariable> findOne(String id) {
        log.debug("Request to get ProgramRuleVariable : {}", id);
        return programRuleVariableRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProgramRuleVariable : {}", id);
        programRuleVariableRepository.deleteById(id);
    }
}
