package com.didate.repository;

import com.didate.domain.DHISUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DHISUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DHISUserRepository extends JpaRepository<DHISUser, String> {}