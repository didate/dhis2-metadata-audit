package com.didate.service.impl;

import com.didate.domain.CategoryCombo;
import com.didate.repository.CategorycomboRepository;
import com.didate.service.CategorycomboService;
import com.didate.service.dto.CategoryComboDTO;
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
 * Service Implementation for managing {@link CategoryCombo}.
 */
@Service
@Transactional
public class CategorycomboServiceImpl implements CategorycomboService {

    private final Logger log = LoggerFactory.getLogger(CategorycomboServiceImpl.class);

    private final CategorycomboRepository categorycomboRepository;

    public CategorycomboServiceImpl(CategorycomboRepository categorycomboRepository) {
        this.categorycomboRepository = categorycomboRepository;
    }

    @Override
    public CategoryCombo save(CategoryCombo categorycombo) {
        log.debug("Request to save Categorycombo : {}", categorycombo);
        return categorycomboRepository.save(categorycombo);
    }

    @Override
    public CategoryCombo update(CategoryCombo categorycombo) {
        log.debug("Request to update Categorycombo : {}", categorycombo);
        categorycombo.setIsPersisted();
        return categorycomboRepository.save(categorycombo);
    }

    @Override
    public Optional<CategoryCombo> partialUpdate(CategoryCombo categorycombo) {
        log.debug("Request to partially update Categorycombo : {}", categorycombo);

        return categorycomboRepository
            .findById(categorycombo.getId())
            .map(existingCategorycombo -> {
                if (categorycombo.getName() != null) {
                    existingCategorycombo.setName(categorycombo.getName());
                }

                return existingCategorycombo;
            })
            .map(categorycomboRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryCombo> findAll(Pageable pageable) {
        log.debug("Request to get all Categorycombos");
        return categorycomboRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryCombo> findOne(String id) {
        log.debug("Request to get Categorycombo : {}", id);
        return categorycomboRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Categorycombo : {}", id);
        categorycomboRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return categorycomboRepository.existsById(id);
    }

    @Override
    public Long count() {
        return categorycomboRepository.count();
    }

    @Override
    public List<CategoryComboDTO> findAudits(String id) {
        return categorycomboRepository
            .findRevisions(id)
            .getContent()
            .stream()
            .map(revision -> {
                CategoryCombo categoryCombo = revision.getEntity();
                Hibernate.unproxy(categoryCombo.getCreatedBy());
                Hibernate.unproxy(categoryCombo.getLastUpdatedBy());
                return new CategoryComboDTO(categoryCombo).revisionNumber(revision.getRequiredRevisionNumber());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryComboDTO findAuditRevision(String id, Integer rev) {
        // Retrieve the revision from the repository
        CategoryCombo categoryCombo = categorycomboRepository
            .findRevision(id, rev)
            .orElseThrow(() -> new EntityNotFoundException("Revision not found"))
            .getEntity();

        Hibernate.unproxy(categoryCombo.getCreatedBy());
        Hibernate.unproxy(categoryCombo.getLastUpdatedBy());

        return new CategoryComboDTO(categoryCombo);
    }
}
