package com.didate.repository;

import com.didate.domain.ProgramStage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProgramStageRepositoryWithBagRelationshipsImpl implements ProgramStageRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ProgramStage> fetchBagRelationships(Optional<ProgramStage> programStage) {
        return programStage.map(this::fetchProgramStageDataElements);
    }

    @Override
    public Page<ProgramStage> fetchBagRelationships(Page<ProgramStage> programStages) {
        return new PageImpl<>(
            fetchBagRelationships(programStages.getContent()),
            programStages.getPageable(),
            programStages.getTotalElements()
        );
    }

    @Override
    public List<ProgramStage> fetchBagRelationships(List<ProgramStage> programStages) {
        return Optional.of(programStages).map(this::fetchProgramStageDataElements).orElse(Collections.emptyList());
    }

    ProgramStage fetchProgramStageDataElements(ProgramStage result) {
        return entityManager
            .createQuery(
                "select programStage from ProgramStage programStage left join fetch programStage.programStageDataElements where programStage is :programStage",
                ProgramStage.class
            )
            .setParameter("programStage", result)
            .getSingleResult();
    }

    List<ProgramStage> fetchProgramStageDataElements(List<ProgramStage> programStages) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, programStages.size()).forEach(index -> order.put(programStages.get(index).getId(), index));
        List<ProgramStage> result = entityManager
            .createQuery(
                "select distinct programStage from ProgramStage programStage left join fetch programStage.programStageDataElements where programStage in :programStages",
                ProgramStage.class
            )
            .setParameter("programStages", programStages)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
/* .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
.setHint(QueryHints.PASS_DISTINCT_THROUGH, false) */
