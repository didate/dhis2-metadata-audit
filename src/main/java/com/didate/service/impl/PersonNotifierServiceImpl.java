package com.didate.service.impl;

import com.didate.domain.PersonNotifier;
import com.didate.repository.PersonNotifierRepository;
import com.didate.service.PersonNotifierService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.PersonNotifier}.
 */
@Service
@Transactional
public class PersonNotifierServiceImpl implements PersonNotifierService {

    private static final Logger log = LoggerFactory.getLogger(PersonNotifierServiceImpl.class);

    private final PersonNotifierRepository personNotifierRepository;

    public PersonNotifierServiceImpl(PersonNotifierRepository personNotifierRepository) {
        this.personNotifierRepository = personNotifierRepository;
    }

    @Override
    public PersonNotifier save(PersonNotifier personNotifier) {
        log.debug("Request to save PersonNotifier : {}", personNotifier);
        return personNotifierRepository.save(personNotifier);
    }

    @Override
    public PersonNotifier update(PersonNotifier personNotifier) {
        log.debug("Request to update PersonNotifier : {}", personNotifier);
        return personNotifierRepository.save(personNotifier);
    }

    @Override
    public Optional<PersonNotifier> partialUpdate(PersonNotifier personNotifier) {
        log.debug("Request to partially update PersonNotifier : {}", personNotifier);

        return personNotifierRepository
            .findById(personNotifier.getId())
            .map(existingPersonNotifier -> {
                if (personNotifier.getPersonName() != null) {
                    existingPersonNotifier.setPersonName(personNotifier.getPersonName());
                }
                if (personNotifier.getPersonPhone() != null) {
                    existingPersonNotifier.setPersonPhone(personNotifier.getPersonPhone());
                }
                if (personNotifier.getPersonEmail() != null) {
                    existingPersonNotifier.setPersonEmail(personNotifier.getPersonEmail());
                }
                if (personNotifier.getPersonOrganization() != null) {
                    existingPersonNotifier.setPersonOrganization(personNotifier.getPersonOrganization());
                }

                return existingPersonNotifier;
            })
            .map(personNotifierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonNotifier> findAll(Pageable pageable) {
        log.debug("Request to get all PersonNotifiers");
        return personNotifierRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonNotifier> findOne(Long id) {
        log.debug("Request to get PersonNotifier : {}", id);
        return personNotifierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonNotifier : {}", id);
        personNotifierRepository.deleteById(id);
    }
}
