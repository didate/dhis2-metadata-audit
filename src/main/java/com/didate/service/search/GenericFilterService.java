package com.didate.service.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class GenericFilterService<T> {

    public Page<T> filter(JpaSpecificationExecutor<T> repository, String id, String name, Pageable pageable) {
        Specification<T> specification = Specification.where(null);

        if (id != null && !id.isEmpty()) {
            specification = specification.and(EntitySpecification.idContains(id));
        }
        if (name != null && !name.isEmpty()) {
            specification = specification.and(EntitySpecification.nameContains(name));
        }

        return repository.findAll(specification, pageable);
    }
}
