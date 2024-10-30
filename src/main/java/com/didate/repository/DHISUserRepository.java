package com.didate.repository;

import com.didate.domain.CategoryCombo;
import com.didate.domain.DHISUser;
import com.didate.domain.enumeration.TypeTrack;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DHISUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DHISUserRepository
    extends JpaRepository<DHISUser, String>, JpaSpecificationExecutor<DHISUser>, RevisionRepository<DHISUser, String, Integer> {
    long countByTrack(TypeTrack track);
}
