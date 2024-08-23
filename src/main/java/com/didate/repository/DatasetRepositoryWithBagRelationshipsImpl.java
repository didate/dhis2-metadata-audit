package com.didate.repository;

import com.didate.domain.Dataset;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DatasetRepositoryWithBagRelationshipsImpl implements DatasetRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String DATASETS_PARAMETER = "datasets";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Dataset> fetchBagRelationships(Optional<Dataset> dataset) {
        return dataset.map(this::fetchDataElements).map(this::fetchOrganisationUnits);
    }

    @Override
    public Page<Dataset> fetchBagRelationships(Page<Dataset> datasets) {
        return new PageImpl<>(fetchBagRelationships(datasets.getContent()), datasets.getPageable(), datasets.getTotalElements());
    }

    @Override
    public List<Dataset> fetchBagRelationships(List<Dataset> datasets) {
        return Optional.of(datasets).map(this::fetchDataElements).map(this::fetchOrganisationUnits).orElse(Collections.emptyList());
    }

    Dataset fetchDataElements(Dataset result) {
        return entityManager
            .createQuery("select dataset from Dataset dataset left join fetch dataset.dataElements where dataset.id = :id", Dataset.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Dataset> fetchDataElements(List<Dataset> datasets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, datasets.size()).forEach(index -> order.put(datasets.get(index).getId(), index));
        List<Dataset> result = entityManager
            .createQuery(
                "select dataset from Dataset dataset left join fetch dataset.dataElements where dataset in :datasets",
                Dataset.class
            )
            .setParameter(DATASETS_PARAMETER, datasets)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Dataset fetchOrganisationUnits(Dataset result) {
        return entityManager
            .createQuery(
                "select dataset from Dataset dataset left join fetch dataset.organisationUnits where dataset.id = :id",
                Dataset.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Dataset> fetchOrganisationUnits(List<Dataset> datasets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, datasets.size()).forEach(index -> order.put(datasets.get(index).getId(), index));
        List<Dataset> result = entityManager
            .createQuery(
                "select dataset from Dataset dataset left join fetch dataset.organisationUnits where dataset in :datasets",
                Dataset.class
            )
            .setParameter(DATASETS_PARAMETER, datasets)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
