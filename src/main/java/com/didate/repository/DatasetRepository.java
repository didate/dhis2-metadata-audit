package com.didate.repository;

import com.didate.domain.DataSet;
import com.didate.domain.enumeration.TypeTrack;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DataSet entity.
 *
 * When extending this class, extend DatasetRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DatasetRepository
    extends
        DatasetRepositoryWithBagRelationships,
        JpaRepository<DataSet, String>,
        JpaSpecificationExecutor<DataSet>,
        RevisionRepository<DataSet, String, Integer> {
    default Optional<DataSet> findOneWithEagerRelationships(String id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DataSet> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DataSet> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    long countByTrack(TypeTrack track);
}
