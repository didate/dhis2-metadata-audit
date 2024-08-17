package com.didate.repository;

import com.didate.domain.PersonNotifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonNotifier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonNotifierRepository extends JpaRepository<PersonNotifier, Long> {}
