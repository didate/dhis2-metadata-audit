package com.didate.service.impl;

import com.didate.domain.ProgramIndicator;
import com.didate.domain.ProgramRuleAction;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramRuleActionRepository;
import com.didate.service.ProgramRuleActionService;
import com.didate.service.dto.ProgramRuleActionDTO;
import com.didate.service.dto.ProgramRuleActionFullDTO;
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
 * Service Implementation for managing {@link ProgramRuleAction}.
 */
@Service
@Transactional
public class ProgramRuleActionServiceImpl implements ProgramRuleActionService {

    private final Logger log = LoggerFactory.getLogger(ProgramRuleActionServiceImpl.class);

    private final ProgramRuleActionRepository programRuleActionRepository;

    private final GenericFilterService<ProgramRuleAction> filterService;

    public ProgramRuleActionServiceImpl(
        ProgramRuleActionRepository programRuleActionRepository,
        GenericFilterService<ProgramRuleAction> filterService
    ) {
        this.programRuleActionRepository = programRuleActionRepository;
        this.filterService = filterService;
    }

    @Override
    public ProgramRuleAction save(ProgramRuleAction programRuleAction) {
        log.debug("Request to save ProgramRuleAction : {}", programRuleAction);
        return programRuleActionRepository.save(programRuleAction);
    }

    @Override
    public ProgramRuleAction update(ProgramRuleAction programRuleAction) {
        log.debug("Request to update ProgramRuleAction : {}", programRuleAction);
        programRuleAction.setIsPersisted();
        return programRuleActionRepository.save(programRuleAction);
    }

    @Override
    public Optional<ProgramRuleAction> partialUpdate(ProgramRuleAction programRuleAction) {
        log.debug("Request to partially update ProgramRuleAction : {}", programRuleAction);

        return programRuleActionRepository
            .findById(programRuleAction.getId())
            .map(existingProgramRuleAction -> {
                if (existingProgramRuleAction.getLastUpdated().equals(programRuleAction.getLastUpdated())) {
                    return existingProgramRuleAction;
                }

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

                if (!programRuleAction.getCreatedBy().equals(existingProgramRuleAction.getCreatedBy())) {
                    existingProgramRuleAction.setCreatedBy(programRuleAction.getCreatedBy());
                }
                if (!programRuleAction.getLastUpdatedBy().equals(existingProgramRuleAction.getLastUpdatedBy())) {
                    existingProgramRuleAction.setLastUpdatedBy(programRuleAction.getLastUpdatedBy());
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

    @Override
    public Long count() {
        return programRuleActionRepository.count();
    }

    @Override
    public List<ProgramRuleActionDTO> findAudits(String id) {
        Pageable pageable = PageRequest.of(0, 20, RevisionSort.desc());
        return programRuleActionRepository
            .findRevisions(id, pageable)
            .getContent()
            .stream()
            .map(revision -> {
                ProgramRuleAction program = revision.getEntity();
                Hibernate.unproxy(program.getCreatedBy());
                Hibernate.unproxy(program.getLastUpdatedBy());
                Hibernate.unproxy(program.getProgramRule());

                return new ProgramRuleActionDTO(program).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramRuleActionFullDTO findAuditRevision(String id, Integer rev) {
        ProgramRuleAction program = programRuleActionRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(program.getCreatedBy());
        Hibernate.unproxy(program.getLastUpdatedBy());
        Hibernate.unproxy(program.getProgramRule());

        return new ProgramRuleActionFullDTO(program);
    }

    @Override
    public Page<ProgramRuleActionDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(programRuleActionRepository, id, name, pageable).map(ProgramRuleActionDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return programRuleActionRepository.countByTrack(track);
    }

    @Override
    public void setTrackNone() {
        programRuleActionRepository.setTrackNone();
    }
}
