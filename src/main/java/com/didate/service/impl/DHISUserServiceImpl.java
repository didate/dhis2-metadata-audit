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
 * Service Implementation for managing {@link com.didate.domain.DHISUser}.
 */
@Service
@Transactional
public class DHISUserServiceImpl implements DHISUserService {

    private static final Logger log = LoggerFactory.getLogger(DHISUserServiceImpl.class);

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
}
