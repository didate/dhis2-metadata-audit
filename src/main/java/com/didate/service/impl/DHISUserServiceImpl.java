package com.didate.service.impl;

import com.didate.domain.DHISUser;
import com.didate.repository.DHISUserRepository;
import com.didate.service.DHISUserService;
import com.didate.service.dto.DHISUserDTO;
import com.didate.service.dto.DHISUserFullDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public DHISUserServiceImpl(DHISUserRepository dHISUserRepository) {
        this.dHISUserRepository = dHISUserRepository;
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
                if (existingDHISUser.getLastUpdated().equals(dHISUser.getLastUpdated())) {
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

                /*  if (!dHISUser.getCreatedBy().equals(existingDHISUser.getCreatedBy())) {
                    existingDHISUser.setCreatedBy(dHISUser.getCreatedBy());
                }
                if (!dHISUser.getLastUpdatedBy().equals(existingDHISUser.getLastUpdatedBy())) {
                    existingDHISUser.setLastUpdatedBy(dHISUser.getLastUpdatedBy());
                } */

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
        return dHISUserRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                DHISUser dHisUser = revision.getEntity();
                // Hibernate.unproxy(dHisUser.getCreatedBy());
                // Hibernate.unproxy(dHisUser.getLastUpdatedBy());
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

        //Hibernate.unproxy(dhisUser.getCreatedBy());
        //Hibernate.unproxy(dhisUser.getLastUpdatedBy());

        return new DHISUserFullDTO(dhisUser);
    }
}
