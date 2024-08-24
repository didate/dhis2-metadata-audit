package com.didate.service.impl;

import com.didate.domain.DHISUser;
import com.didate.repository.DHISUserRepository;
import com.didate.service.DHISUserService;
import java.util.Optional;
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
                if (dHISUser.getCode() != null) {
                    existingDHISUser.setCode(dHISUser.getCode());
                }
                if (dHISUser.getName() != null) {
                    existingDHISUser.setName(dHISUser.getName());
                }
                if (dHISUser.getDisplayName() != null) {
                    existingDHISUser.setDisplayName(dHISUser.getDisplayName());
                }
                if (dHISUser.getUsername() != null) {
                    existingDHISUser.setUsername(dHISUser.getUsername());
                }
                if (dHISUser.getLastLogin() != null) {
                    existingDHISUser.setLastLogin(dHISUser.getLastLogin());
                }
                if (dHISUser.getEmail() != null) {
                    existingDHISUser.setEmail(dHISUser.getEmail());
                }
                if (dHISUser.getPhoneNumber() != null) {
                    existingDHISUser.setPhoneNumber(dHISUser.getPhoneNumber());
                }
                if (dHISUser.getDisabled() != null) {
                    existingDHISUser.setDisabled(dHISUser.getDisabled());
                }
                if (dHISUser.getPasswordLastUpdated() != null) {
                    existingDHISUser.setPasswordLastUpdated(dHISUser.getPasswordLastUpdated());
                }
                if (dHISUser.getCreated() != null) {
                    existingDHISUser.setCreated(dHISUser.getCreated());
                }
                if (dHISUser.getLastUpdated() != null) {
                    existingDHISUser.setLastUpdated(dHISUser.getLastUpdated());
                }
                if (dHISUser.getTrack() != null) {
                    existingDHISUser.setTrack(dHISUser.getTrack());
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
}
