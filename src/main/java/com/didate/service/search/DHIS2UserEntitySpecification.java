package com.didate.service.search;

import java.time.Instant;
import org.springframework.data.jpa.domain.Specification;

public class DHIS2UserEntitySpecification {

    private DHIS2UserEntitySpecification() {}

    public static <T> Specification<T> usernameContains(String username) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static <T> Specification<T> lastLoginBefore(Instant date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("lastLogin"), date);
    }

    public static <T> Specification<T> disabled(Boolean disabled) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("disabled"), disabled);
    }
}
