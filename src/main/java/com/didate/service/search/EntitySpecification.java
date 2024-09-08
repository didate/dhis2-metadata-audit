package com.didate.service.search;

import org.springframework.data.jpa.domain.Specification;

public class EntitySpecification {

    private EntitySpecification() {}

    public static <T> Specification<T> idContains(String id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), "%" + id + "%");
    }

    public static <T> Specification<T> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
