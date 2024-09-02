package com.didate.repository;

import com.didate.domain.DataSet;
import java.util.List;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DatasetRepositoryWithBagRelationships {
    Optional<DataSet> fetchBagRelationships(Optional<DataSet> dataset);

    List<DataSet> fetchBagRelationships(List<DataSet> datasets);

    Page<DataSet> fetchBagRelationships(Page<DataSet> datasets);
}
