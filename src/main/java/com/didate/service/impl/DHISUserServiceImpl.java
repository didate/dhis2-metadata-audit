package com.didate.service.impl;

import com.didate.domain.DHISUser;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.DHISUserRepository;
import com.didate.service.DHISUserService;
import com.didate.service.dto.DHISUserDTO;
import com.didate.service.dto.DHISUserFullDTO;
import com.didate.service.search.DHIS2UserFilterService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.converters.models.DefaultPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.RevisionSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DHISUser}.
 */
@Service
@Transactional
public class DHISUserServiceImpl implements DHISUserService {

    private final Logger log = LoggerFactory.getLogger(DHISUserServiceImpl.class);

    private final DHISUserRepository dHISUserRepository;

    private final DHIS2UserFilterService filterService;

    public DHISUserServiceImpl(DHISUserRepository dHISUserRepository, DHIS2UserFilterService filterService) {
        this.dHISUserRepository = dHISUserRepository;
        this.filterService = filterService;
    }

    @Override
    public DHISUser save(DHISUser dHISUser) {
        log.debug("Request to save DHISUser : {}", dHISUser);
        return dHISUserRepository.save(dHISUser);
    }

    @Override
    public DHISUser update(DHISUser dHISUser) {
        log.debug("Request to update DHISUser : {}", dHISUser);
        dHISUser.setIsPersisted();
        return dHISUserRepository.save(dHISUser);
    }

    @Override
    public Optional<DHISUser> partialUpdate(DHISUser dHISUser) {
        log.debug("Request to partially update DHISUser : {}", dHISUser);

        return dHISUserRepository
            .findById(dHISUser.getId())
            .map(existingDHISUser -> {
                if (existingDHISUser.getLastUpdated() == dHISUser.getLastUpdated()) {
                    return existingDHISUser;
                }
                existingDHISUser.setCode(dHISUser.getCode());
                existingDHISUser.setName(dHISUser.getName());
                existingDHISUser.setDisplayName(dHISUser.getDisplayName());
                existingDHISUser.setUsername(dHISUser.getUsername());
                existingDHISUser.setLastLogin(dHISUser.getLastLogin());
                existingDHISUser.setEmail(dHISUser.getEmail());
                existingDHISUser.setPhoneNumber(dHISUser.getPhoneNumber());
                existingDHISUser.setDisabled(dHISUser.getDisabled());
                existingDHISUser.setPasswordLastUpdated(dHISUser.getPasswordLastUpdated());
                existingDHISUser.setCreated(dHISUser.getCreated());
                existingDHISUser.setLastUpdated(dHISUser.getLastUpdated());
                existingDHISUser.setTrack(dHISUser.getTrack());

                if (!Objects.equals(existingDHISUser.getCreatedBy(), dHISUser.getCreatedBy())) {
                    existingDHISUser.setCreatedBy(dHISUser.getCreatedBy());
                }
                if (!Objects.equals(existingDHISUser.getLastUpdatedBy(), dHISUser.getLastUpdatedBy())) {
                    existingDHISUser.setLastUpdatedBy(dHISUser.getLastUpdatedBy());
                }

                return existingDHISUser;
            })
            .map(dHISUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DHISUser> findAll(Pageable pageable) {
        log.debug("Request to get all DHISUsers");
        return dHISUserRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DHISUser> findOne(String id) {
        log.debug("Request to get DHISUser : {}", id);
        return dHISUserRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete DHISUser : {}", id);
        dHISUserRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return dHISUserRepository.existsById(id);
    }

    @Override
    public Long count() {
        return dHISUserRepository.count();
    }

    @Override
    public List<DHISUserDTO> findAudits(String id) {
        Pageable pageable = PageRequest.of(0, 20, RevisionSort.desc());
        return dHISUserRepository
            .findRevisions(id, pageable)
            .getContent()
            .stream()
            .map(revision -> {
                DHISUser dHisUser = revision.getEntity();
                Hibernate.unproxy(dHisUser.getCreatedBy());
                Hibernate.unproxy(dHisUser.getLastUpdatedBy());
                return new DHISUserDTO(dHisUser).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DHISUserFullDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        DHISUser dhisUser = dHISUserRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(dhisUser.getCreatedBy());
        Hibernate.unproxy(dhisUser.getLastUpdatedBy());

        return new DHISUserFullDTO(dhisUser);
    }

    @Override
    public Page<DHISUserDTO> findAll(Pageable pageable, String id, String name, String username, Integer months, Boolean disabled) {
        return filterService.filter(dHISUserRepository, id, name, username, months, disabled, pageable).map(DHISUserDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return dHISUserRepository.countByTrack(track);
    }
}
