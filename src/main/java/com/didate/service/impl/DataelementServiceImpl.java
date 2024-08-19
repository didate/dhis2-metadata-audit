package com.didate.service.impl;

import com.didate.domain.Dataelement;
import com.didate.repository.DataelementRepository;
import com.didate.service.DataelementService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.didate.domain.Dataelement}.
 */
@Service
@Transactional
public class DataelementServiceImpl implements DataelementService {

    private static final Logger log = LoggerFactory.getLogger(DataelementServiceImpl.class);

    private final DataelementRepository dataelementRepository;

    public DataelementServiceImpl(DataelementRepository dataelementRepository) {
        this.dataelementRepository = dataelementRepository;
    }

    @Override
    public Dataelement save(Dataelement dataelement) {
        log.debug("Request to save Dataelement : {}", dataelement);
        return dataelementRepository.save(dataelement);
    }

    @Override
    public Dataelement update(Dataelement dataelement) {
        log.debug("Request to update Dataelement : {}", dataelement);
        return dataelementRepository.save(dataelement);
    }

    @Override
    public Optional<Dataelement> partialUpdate(Dataelement dataelement) {
        log.debug("Request to partially update Dataelement : {}", dataelement);

        return dataelementRepository
            .findById(dataelement.getId())
            .map(existingDataelement -> {
                if (dataelement.getName() != null) {
                    existingDataelement.setName(dataelement.getName());
                }
                if (dataelement.getShortName() != null) {
                    existingDataelement.setShortName(dataelement.getShortName());
                }
                if (dataelement.getFormName() != null) {
                    existingDataelement.setFormName(dataelement.getFormName());
                }
                if (dataelement.getDescription() != null) {
                    existingDataelement.setDescription(dataelement.getDescription());
                }
                if (dataelement.getDisplayShortName() != null) {
                    existingDataelement.setDisplayShortName(dataelement.getDisplayShortName());
                }
                if (dataelement.getDisplayName() != null) {
                    existingDataelement.setDisplayName(dataelement.getDisplayName());
                }
                if (dataelement.getDisplayFormName() != null) {
                    existingDataelement.setDisplayFormName(dataelement.getDisplayFormName());
                }
                if (dataelement.getCreated() != null) {
                    existingDataelement.setCreated(dataelement.getCreated());
                }
                if (dataelement.getLastUpdated() != null) {
                    existingDataelement.setLastUpdated(dataelement.getLastUpdated());
                }
                if (dataelement.getPublicAccess() != null) {
                    existingDataelement.setPublicAccess(dataelement.getPublicAccess());
                }
                if (dataelement.getDimensionItemType() != null) {
                    existingDataelement.setDimensionItemType(dataelement.getDimensionItemType());
                }
                if (dataelement.getAggregationType() != null) {
                    existingDataelement.setAggregationType(dataelement.getAggregationType());
                }
                if (dataelement.getValueType() != null) {
                    existingDataelement.setValueType(dataelement.getValueType());
                }
                if (dataelement.getDomainType() != null) {
                    existingDataelement.setDomainType(dataelement.getDomainType());
                }
                if (dataelement.getZeroIsSignificant() != null) {
                    existingDataelement.setZeroIsSignificant(dataelement.getZeroIsSignificant());
                }
                if (dataelement.getOptionSetValue() != null) {
                    existingDataelement.setOptionSetValue(dataelement.getOptionSetValue());
                }
                if (dataelement.getDimensionItem() != null) {
                    existingDataelement.setDimensionItem(dataelement.getDimensionItem());
                }

                return existingDataelement;
            })
            .map(dataelementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dataelement> findAll(Pageable pageable) {
        log.debug("Request to get all Dataelements");
        return dataelementRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dataelement> findOne(String id) {
        log.debug("Request to get Dataelement : {}", id);
        return dataelementRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Dataelement : {}", id);
        dataelementRepository.deleteById(id);
    }

    @Override
    public Boolean exist(String id) {
        return dataelementRepository.existsById(id);
    }
}
