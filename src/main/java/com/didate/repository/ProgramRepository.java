package com.didate.repository;

import com.didate.domain.Program;
import com.didate.domain.enumeration.TypeTrack;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Program entity.
 *
 * When extending this class, extend ProgramRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ProgramRepository
    extends
        ProgramRepositoryWithBagRelationships,
        JpaRepository<Program, String>,
        JpaSpecificationExecutor<Program>,
        RevisionRepository<Program, String, Integer> {
    default Optional<Program> findOneWithEagerRelationships(String id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Program> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Program> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    long countByTrack(TypeTrack track);

    @Modifying
    @Query(nativeQuery = true, value = "update program set track='NONE'")
    void setTrackNone();
}
