package com.didate.service.impl;

import com.didate.domain.ProgramRuleVariable;
import com.didate.repository.ProgramRuleVariableRepository;
import com.didate.service.ProgramRuleVariableService;
import com.didate.service.dto.ProgramRuleVariableDTO;
import com.didate.service.dto.ProgramRuleVariableFullDTO;
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
 * Service Implementation for managing {@link ProgramRuleVariable}.
 */
@Service
@Transactional
public class ProgramRuleVariableServiceImpl implements ProgramRuleVariableService {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleVariableServiceImpl.class);

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
        programRuleVariable.setIsPersisted();
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

    @Override
    public Boolean exist(String id) {
        return programRuleVariableRepository.existsById(id);
    }

    @Override
    public Long count() {
        return programRuleVariableRepository.count();
    }

    @Override
    public List<ProgramRuleVariableDTO> findAudits(String id) {
        return programRuleVariableRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                ProgramRuleVariable progamRuleVariable = revision.getEntity();
                Hibernate.unproxy(progamRuleVariable.getCreatedBy());
                Hibernate.unproxy(progamRuleVariable.getLastUpdatedBy());

                return new ProgramRuleVariableDTO(progamRuleVariable).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramRuleVariableFullDTO findAuditRevision(String id, Integer rev) {
        ProgramRuleVariable programRuleVariable = programRuleVariableRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(programRuleVariable.getCreatedBy());
        Hibernate.unproxy(programRuleVariable.getLastUpdatedBy());

        return new ProgramRuleVariableFullDTO(programRuleVariable);
    }
}
