package com.didate.service.search;

import com.didate.domain.DHISUser;
import com.didate.repository.DHISUserRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DHIS2UserFilterService {

    public Page<DHISUser> filter(
        DHISUserRepository userRepository,
        String id,
        String name,
        String username,
        Integer months,
        Pageable pageable
    ) {
        Specification<DHISUser> specification = Specification.where(null);

        if (id != null && !id.isEmpty()) {
            specification = specification.and(EntitySpecification.idContains(id));
        }
        if (name != null && !name.isEmpty()) {
            specification = specification.and(EntitySpecification.nameContains(name));
        }

        if (username != null && !username.isEmpty()) {
            specification = specification.and(DHIS2UserEntitySpecification.usernameContains(username));
        }
        if (months != null && months != 0) {
            Instant monthsAgo = Instant.now().minus(30l * months, ChronoUnit.DAYS);
            specification = specification.and(DHIS2UserEntitySpecification.lastLoginBefore(monthsAgo));
        }
        return userRepository.findAll(specification, pageable);
    }
}
