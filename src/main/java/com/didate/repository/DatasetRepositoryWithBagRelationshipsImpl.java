package com.didate.repository;

import com.didate.domain.DataSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DatasetRepositoryWithBagRelationshipsImpl implements DatasetRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DataSet> fetchBagRelationships(Optional<DataSet> dataset) {
        return dataset.map(this::fetchDataSetElements).map(this::fetchIndicators).map(this::fetchOrganisationUnits);
    }

    @Override
    public Page<DataSet> fetchBagRelationships(Page<DataSet> datasets) {
        return new PageImpl<>(fetchBagRelationships(datasets.getContent()), datasets.getPageable(), datasets.getTotalElements());
    }

    @Override
    public List<DataSet> fetchBagRelationships(List<DataSet> datasets) {
        return Optional
            .of(datasets)
            .map(this::fetchDataSetElements)
            .map(this::fetchIndicators)
            .map(this::fetchOrganisationUnits)
            .orElse(Collections.emptyList());
    }

    DataSet fetchDataSetElements(DataSet result) {
        return entityManager
            .createQuery(
                "select dataset from DataSet dataset left join fetch dataset.dataSetElements where dataset is :dataset",
                DataSet.class
            )
            .setParameter("dataset", result)
            .getSingleResult();
    }

    List<DataSet> fetchDataSetElements(List<DataSet> datasets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, datasets.size()).forEach(index -> order.put(datasets.get(index).getId(), index));
        List<DataSet> result = entityManager
            .createQuery(
                "select distinct dataset from DataSet dataset left join fetch dataset.dataSetElements where dataset in :datasets",
                DataSet.class
            )
            .setParameter("datasets", datasets)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    DataSet fetchIndicators(DataSet result) {
        return entityManager
            .createQuery("select dataset from DataSet dataset left join fetch dataset.indicators where dataset is :dataset", DataSet.class)
            .setParameter("dataset", result)
            .getSingleResult();
    }

    List<DataSet> fetchIndicators(List<DataSet> datasets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, datasets.size()).forEach(index -> order.put(datasets.get(index).getId(), index));
        List<DataSet> result = entityManager
            .createQuery(
                "select distinct dataset from DataSet dataset left join fetch dataset.indicators where dataset in :datasets",
                DataSet.class
            )
            .setParameter("datasets", datasets)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    DataSet fetchOrganisationUnits(DataSet result) {
        return entityManager
            .createQuery(
                "select dataset from DataSet dataset left join fetch dataset.organisationUnits where dataset is :dataset",
                DataSet.class
            )
            .setParameter("dataset", result)
            .getSingleResult();
    }

    List<DataSet> fetchOrganisationUnits(List<DataSet> datasets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, datasets.size()).forEach(index -> order.put(datasets.get(index).getId(), index));
        List<DataSet> result = entityManager
            .createQuery(
                "select distinct dataset from DataSet dataset left join fetch dataset.organisationUnits where dataset in :datasets",
                DataSet.class
            )
            .setParameter("datasets", datasets)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
/* .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
.setHint(QueryHints.PASS_DISTINCT_THROUGH, false) */
