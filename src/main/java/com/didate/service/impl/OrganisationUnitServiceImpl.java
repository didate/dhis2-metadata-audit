package com.didate.service.impl;

import com.didate.domain.OrganisationUnit;
import com.didate.domain.Program;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.OrganisationUnitRepository;
import com.didate.service.OrganisationUnitService;
import com.didate.service.dto.OrganisationUnitDTO;
import com.didate.service.dto.OrganisationUnitFullDTO;
import com.didate.service.dto.ProgramDTO;
import com.didate.service.dto.ProgramFullDTO;
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
 * Service Implementation for managing {@link OrganisationUnit}.
 */
@Service
@Transactional
public class OrganisationUnitServiceImpl implements OrganisationUnitService {

    private final Logger log = LoggerFactory.getLogger(OrganisationUnitServiceImpl.class);

    private final OrganisationUnitRepository organisationUnitRepository;
    private final GenericFilterService<OrganisationUnit> filterService;

    public OrganisationUnitServiceImpl(
        OrganisationUnitRepository organisationUnitRepository,
        GenericFilterService<OrganisationUnit> filterService
    ) {
        this.organisationUnitRepository = organisationUnitRepository;
        this.filterService = filterService;
    }

    @Override
    public OrganisationUnit save(OrganisationUnit organisationUnit) {
        log.debug("Request to save OrganisationUnit : {}", organisationUnit);
        return organisationUnitRepository.save(organisationUnit);
    }

    @Override
    public OrganisationUnit update(OrganisationUnit organisationUnit) {
        log.debug("Request to update OrganisationUnit : {}", organisationUnit);
        organisationUnit.setIsPersisted();
        return organisationUnitRepository.save(organisationUnit);
    }

    @Override
    public Optional<OrganisationUnit> partialUpdate(OrganisationUnit organisationUnit) {
        log.debug("Request to partially update OrganisationUnit : {}", organisationUnit);

        return organisationUnitRepository
            .findById(organisationUnit.getId())
            .map(existingOrganisationUnit -> {
                if (existingOrganisationUnit.getLastUpdated().equals(organisationUnit.getLastUpdated())) {
                    return existingOrganisationUnit;
                }

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

                if (!organisationUnit.getCreatedBy().equals(existingOrganisationUnit.getCreatedBy())) {
                    existingOrganisationUnit.setCreatedBy(organisationUnit.getCreatedBy());
                }
                if (!organisationUnit.getLastUpdatedBy().equals(existingOrganisationUnit.getLastUpdatedBy())) {
                    existingOrganisationUnit.setLastUpdatedBy(organisationUnit.getLastUpdatedBy());
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

    @Override
    public Boolean exist(String id) {
        return organisationUnitRepository.existsById(id);
    }

    @Override
    public Long count() {
        return organisationUnitRepository.count();
    }

    @Override
    public List<OrganisationUnitDTO> findAudits(String id) {
        return organisationUnitRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                OrganisationUnit organisationUnit = revision.getEntity();
                Hibernate.unproxy(organisationUnit.getCreatedBy());
                Hibernate.unproxy(organisationUnit.getLastUpdatedBy());
                return new OrganisationUnitDTO(organisationUnit).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrganisationUnitFullDTO findAuditRevision(String id, Integer rev) {
        OrganisationUnit organisationUnit = organisationUnitRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(organisationUnit.getCreatedBy());
        Hibernate.unproxy(organisationUnit.getLastUpdatedBy());

        return new OrganisationUnitFullDTO(organisationUnit);
    }

    @Override
    public Page<OrganisationUnitDTO> findAll(Pageable pageable, String id, String name) {
        return filterService.filter(organisationUnitRepository, id, name, pageable).map(OrganisationUnitDTO::new);
    }

    @Override
    public long countByTrack(TypeTrack track) {
        return organisationUnitRepository.countByTrack(track);
    }
}
