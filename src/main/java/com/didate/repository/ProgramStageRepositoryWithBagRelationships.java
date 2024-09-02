package com.didate.repository;

import com.didate.domain.ProgramStage;
import java.util.List;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProgramStageRepositoryWithBagRelationships {
    Optional<ProgramStage> fetchBagRelationships(Optional<ProgramStage> programStage);

    List<ProgramStage> fetchBagRelationships(List<ProgramStage> programStages);

    Page<ProgramStage> fetchBagRelationships(Page<ProgramStage> programStages);
}
