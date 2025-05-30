package com.kapitonau.taskservice.repository.custom.impl

import com.kapitonau.projectstudio.taskservice.dto.StatusTaskResponse
import com.kapitonau.taskservice.model.TaskModel
import com.kapitonau.taskservice.repository.custom.CustomTaskRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.stereotype.Repository

@Repository
class CustomTaskRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager
) : CustomTaskRepository {
    override fun countTasksByFilters(): Long {


        val builder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<Long> = builder.createQuery(Long::class.java)
        val root: Root<TaskModel> = criteria.from(TaskModel::class.java)

        criteria.select(builder.count(root))

        return entityManager.createQuery(criteria)
            .singleResult
    }

    override fun getTasksByFilters(
        offset: Long?,
        limit: Long?
    ): List<TaskModel> {
        val builder = entityManager.criteriaBuilder
        val criteria = builder.createQuery(TaskModel::class.java)
        val root = criteria.from(TaskModel::class.java)

        criteria.select(root)
            .where()

        val result = entityManager.createQuery(criteria)
        result.firstResult = offset?.toInt()!!
        result.maxResults = limit?.toInt()!!

        return result.resultList
    }

    override fun getTaskByStatus(): List<StatusTaskResponse> {
        TODO("Not yet implemented")
    }

}