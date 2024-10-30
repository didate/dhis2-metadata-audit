package com.didate.service.impl;

import com.didate.domain.OptionSet;
import com.didate.domain.OrganisationUnit;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.OptionsetRepository;
import com.didate.service.OptionsetService;
import com.didate.service.dto.OptionSetDTO;
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
 * Service Implementation for managing {@link OptionSet}.
 */
@Service
@Transactional
public class OptionsetServiceImpl implements OptionsetService {

    private final Logger log = LoggerFactory.getLogger(OptionsetServiceImpl.class);

    private final OptionsetRepository optionsetRepository;
    private final GenericFilterService<OptionSet> filterService;

    public OptionsetServiceImpl(OptionsetRepository optionsetRepository, GenericFilterService<OptionSet> filterService) {
        this.optionsetRepository = optionsetRepository;
        this.filterService = filterService;
    }

    @Override
    public OptionSet save(OptionSet optionset) {
        log.debug("Request to save OptionSet : {}", optionset);
        return optionsetRepository.save(optionset);
    }

    @Override
    public OptionSet update(OptionSet optionset) {
        log.debug("Request to update OptionSet : {}", optionset);
        optionset.setIsPersisted();
        return optionsetRepository.save(optionset);
    }

    @Override
    public Optional<OptionSet> partialUpdate(OptionSet optionset) {
        log.debug("Request to partially update OptionSet : {}", optionset);

        return optionsetRepository
            .findById(optionset.getId())
            .map(existingOptionset -> {
                if (optionset.getName() != null) {
                    existingOptionset.setName(optionset.getName());
                }
                if (!optionset.getName().equals(existingOptionset.getName())) {
                    existingOptionset.setName(optionset.getName());
                }
                if (!optionset.getShortName().equals(existingOptionset.getShortName())) {
                    existingOptionset.setShortName(optionset.getShortName());
                }
                if (!optionset.getTrack().equals(existingOptionset.getTrack())) {
                    existingOptionset.setTrack(optionset.getTrack());
                }

                if (optionset.getCreated() != null) {
                    existingOptionset.setCreated(optionset.getCreated());
                }
                if (optionset.getLastUpdated() != null) {
                    existingOptionset.setLastUpdated(optionset.getLastUpdated());
                }

                if (!optionset.getCreatedBy().equals(existingOptionset.getCreatedBy())) {
                    existingOptionset.setCreatedBy(optionset.getCreatedBy());
                }
                if (!optionset.getLastUpdatedBy().equals(existingOptionset.getLastUpdatedBy())) {
                    existingOptionset.setLastUpdatedBy(optionset.getLastUpdatedBy());
                }

                return existingOptionset;
            })
            .map(optionsetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptionSet> findAll(Pageable pageable) {
        log.debug("Request to get all Optionsets");
        return optionsetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OptionSet> findOne(String id) {
        log.debug("Request to get OptionSet : {}", id);
        return optionsetRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OptionSet : {}", id);
        optionsetRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return optionsetRepository.existsById(id);
    }

    @Override
    public Long count() {
        return optionsetRepository.count();
    }

    @Override
    public List<OptionSetDTO> findAudits(String id) {
        return optionsetRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                OptionSet optionSet = revision.getEntity();
                Hibernate.unproxy(optionSet.getCreatedBy());
                Hibernate.unproxy(optionSet.getLastUpdatedBy());
                return new OptionSetDTO(optionSet).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OptionSetDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        OptionSet optionSet = optionsetRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(optionSet.getCreatedBy());
        Hibernate.unproxy(optionSet.getLastUpdatedBy());

        return new OptionSetDTO(optionSet);
    }

    @Override
    public Page<OptionSetDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(optionsetRepository, id, name, pageable).map(OptionSetDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return optionsetRepository.countByTrack(track);
    }
}
