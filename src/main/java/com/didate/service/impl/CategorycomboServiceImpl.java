package com.didate.service.impl;

import com.didate.domain.Categorycombo;
import com.didate.repository.CategorycomboRepository;
import com.didate.service.CategorycomboService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.Categorycombo}.
 */
@Service
@Transactional
public class CategorycomboServiceImpl implements CategorycomboService {

    private static final Logger log = LoggerFactory.getLogger(CategorycomboServiceImpl.class);

    private final CategorycomboRepository categorycomboRepository;

    public CategorycomboServiceImpl(CategorycomboRepository categorycomboRepository) {
        this.categorycomboRepository = categorycomboRepository;
    }

    @Override
    public Categorycombo save(Categorycombo categorycombo) {
        log.debug("Request to save Categorycombo : {}", categorycombo);
        return categorycomboRepository.save(categorycombo);
    }

    @Override
    public Categorycombo update(Categorycombo categorycombo) {
        log.debug("Request to update Categorycombo : {}", categorycombo);
        return categorycomboRepository.save(categorycombo);
    }

    @Override
    public Optional<Categorycombo> partialUpdate(Categorycombo categorycombo) {
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
    public Page<Categorycombo> findAll(Pageable pageable) {
        log.debug("Request to get all Categorycombos");
        return categorycomboRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categorycombo> findOne(String id) {
        log.debug("Request to get Categorycombo : {}", id);
        return categorycomboRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Categorycombo : {}", id);
        categorycomboRepository.deleteById(id);
    }
}
