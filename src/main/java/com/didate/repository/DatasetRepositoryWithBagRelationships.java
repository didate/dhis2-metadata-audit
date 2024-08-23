package com.didate.repository;

import com.didate.domain.Dataset;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DatasetRepositoryWithBagRelationships {
    Optional<Dataset> fetchBagRelationships(Optional<Dataset> dataset);

    List<Dataset> fetchBagRelationships(List<Dataset> datasets);

    Page<Dataset> fetchBagRelationships(Page<Dataset> datasets);
}
