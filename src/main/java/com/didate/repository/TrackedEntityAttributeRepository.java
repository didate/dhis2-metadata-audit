package com.didate.repository;

import com.didate.domain.TrackedEntityAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrackedEntityAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackedEntityAttributeRepository extends JpaRepository<TrackedEntityAttribute, String> {}
