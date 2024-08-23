package com.didate.service.impl;

import com.didate.domain.OrganisationUnit;
import com.didate.repository.OrganisationUnitRepository;
import com.didate.service.OrganisationUnitService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.OrganisationUnit}.
 */
@Service
@Transactional
public class OrganisationUnitServiceImpl implements OrganisationUnitService {

    private static final Logger log = LoggerFactory.getLogger(OrganisationUnitServiceImpl.class);

    private final OrganisationUnitRepository organisationUnitRepository;

    public OrganisationUnitServiceImpl(OrganisationUnitRepository organisationUnitRepository) {
        this.organisationUnitRepository = organisationUnitRepository;
    }

    @Override
    public OrganisationUnit save(OrganisationUnit organisationUnit) {
        log.debug("Request to save OrganisationUnit : {}", organisationUnit);
        return organisationUnitRepository.save(organisationUnit);
    }

    @Override
    public OrganisationUnit update(OrganisationUnit organisationUnit) {
        log.debug("Request to update OrganisationUnit : {}", organisationUnit);
        return organisationUnitRepository.save(organisationUnit);
    }

    @Override
    public Optional<OrganisationUnit> partialUpdate(OrganisationUnit organisationUnit) {
        log.debug("Request to partially update OrganisationUnit : {}", organisationUnit);

        return organisationUnitRepository
            .findById(organisationUnit.getId())
            .map(existingOrganisationUnit -> {
                if (organisationUnit.getName() != null) {
                    existingOrganisationUnit.setName(organisationUnit.getName());
                }
                if (organisationUnit.getCreated() != null) {
                    existingOrganisationUnit.setCreated(organisationUnit.getCreated());
                }
                if (organisationUnit.getLastUpdated() != null) {
                    existingOrganisationUnit.setLastUpdated(organisationUnit.getLastUpdated());
                }
                if (organisationUnit.getPath() != null) {
                    existingOrganisationUnit.setPath(organisationUnit.getPath());
                }
                if (organisationUnit.getOpeningDate() != null) {
                    existingOrganisationUnit.setOpeningDate(organisationUnit.getOpeningDate());
                }
                if (organisationUnit.getLevel() != null) {
                    existingOrganisationUnit.setLevel(organisationUnit.getLevel());
                }
                if (organisationUnit.getTrack() != null) {
                    existingOrganisationUnit.setTrack(organisationUnit.getTrack());
                }

                return existingOrganisationUnit;
            })
            .map(organisationUnitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganisationUnit> findAll(Pageable pageable) {
        log.debug("Request to get all OrganisationUnits");
        return organisationUnitRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganisationUnit> findOne(String id) {
        log.debug("Request to get OrganisationUnit : {}", id);
        return organisationUnitRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OrganisationUnit : {}", id);
        organisationUnitRepository.deleteById(id);
    }
}
