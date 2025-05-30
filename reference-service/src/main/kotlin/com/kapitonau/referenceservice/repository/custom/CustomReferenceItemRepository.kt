/*
package com.kapitonau.referenceservice.repository.custom

import com.kapitonau.referenceservice.model.ReferenceItemModel
import com.kapitonau.referenceservice.model.ReferencesTypeModel
import com.kapitonau.referenceservice.repository.CustomReferenceItemRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Repository

@Repository
class CustomReferenceItemRepository(
    @PersistenceContext private val entityManager: EntityManager,
) : CustomReferenceItemRepository {

    override fun findAllByReferenceTypeAndItemCode(
        referenceType: String,
        itemCode: String?
    ): List<ReferenceItemModel> {

        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(ReferenceItemModel::class.java)
        val root = query.from(ReferenceItemModel::class.java)

        val predicateList = getPredicateByFilter(builder, root, referenceType, itemCode).toTypedArray()
        val predicates = emptyArray<Predicate>()
        for ((index, predicate) in predicateList.withIndex()) {
            predicates[index] = predicate
        }

        query.select(root)
            .where(builder.and(*predicates))

        return entityManager.createQuery(query).resultList
    }

    private fun getPredicateByFilter(
        builder: CriteriaBuilder,
        root: Root<ReferenceItemModel>,
        referenceType: String,
        itemCode: String?
    ): MutableList<Predicate> {
        val predicates = mutableListOf<Predicate>()

        if (referenceType.isNotEmpty()) {
            predicates.add(
                builder.equal(
                    root.get<ReferencesTypeModel>("referenceType").get<String>("referenceType"),
                    referenceType
                )
            )
        }

        if (itemCode != null && itemCode.isNotEmpty()) {
            predicates.add(builder.equal(root.get<String>("itemCode"), itemCode))
        }

        return predicates;
    }
}*/
