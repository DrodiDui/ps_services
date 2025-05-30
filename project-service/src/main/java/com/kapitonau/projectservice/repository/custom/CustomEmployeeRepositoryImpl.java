package com.kapitonau.projectservice.repository.custom;

import com.kapitonau.projectservice.model.EmployeeModel;
import com.kapitonau.projectservice.model.SpaceEmployeeModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EmployeeModel> findAllByFilters(Long offset, Long limit, Long spaceId) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EmployeeModel> criteria = builder.createQuery(EmployeeModel.class);
        Root<EmployeeModel> root = criteria.from(EmployeeModel.class);

        Join<EmployeeModel, SpaceEmployeeModel> spaceEmployeeJoin = root.join("spaceEmployees", JoinType.INNER);

        criteria.select(root)
                .where(preparePredicates(builder, root, spaceEmployeeJoin, spaceId).toArray(Predicate[]::new));

        return getEntityManager()
                .createQuery(criteria)
                /*.setFirstResult(offset.intValue())
                .setMaxResults(limit.intValue())*/
                .getResultList();
    }

    @Override
    public Long countEmployees(Long spaceId) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<EmployeeModel> root = query.from(EmployeeModel.class);

        Join<EmployeeModel, SpaceEmployeeModel> spaceEmployeeJoin = root.join("spaceEmployees", JoinType.INNER);

        query.select(builder.count(root))
                .where(preparePredicates(builder, root, spaceEmployeeJoin, spaceId).toArray(Predicate[]::new));

        return getEntityManager().createQuery(query).getSingleResult();
    }

    private List<Predicate> preparePredicates(
            CriteriaBuilder builder,
            Root<EmployeeModel> root,
            Join<EmployeeModel, SpaceEmployeeModel> spaceEmployeeJoin,
            Long spaceId
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (spaceId != null) {
            predicates.add(builder.equal(root.get("spaceEmployees").get("spaceEmployeeId").get("spaceId"), spaceId));
        }

        return predicates;
    }

}
