package com.kapitonau.ps.aggregatorservice.repository.custom.impl

import com.kapitonau.ps.aggregatorservice.model.WorkspaceModel
import com.kapitonau.ps.aggregatorservice.repository.custom.CustomWorkspaceRepository
import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.Join
import org.springframework.stereotype.Repository
import java.util.function.Predicate
import kotlin.streams.toList

@Repository
class CustomWorkspaceRepositoryImpl(
    private @PersistenceContext val entityManager: EntityManager
) : CustomWorkspaceRepository {
    override fun countAvailableMemberWorkspace(ownerId: Long): Long {

        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Long::class.java)
        val root = query.from<WorkspaceModel>(WorkspaceModel::class.java)

        query.select(builder.count(root))
            .where(builder.equal(root.get<WorkspaceModel>("ownerId"), ownerId))

        return entityManager.createQuery(query).singleResult
    }

    override fun getAvailableMemberWorkspace(
        offset: Long,
        limit: Long,
        ownerId: Long
    ): List<WorkspaceResponse> {

        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(WorkspaceModel::class.java)
        val root = query.from<WorkspaceModel>(WorkspaceModel::class.java)

        query.select(root)
            .where(builder.equal(root.get<WorkspaceModel>("ownerId"), ownerId))

        val createQuery = entityManager.createQuery(query)
        createQuery.firstResult = offset.toInt()
        createQuery.maxResults = limit.toInt()
        return createQuery.resultStream
            .map { it -> WorkspaceResponse(
                it.workspaceId,
                it.workspaceName,
                it.description,
                it.createdDate,
                null
            ) }
            .toList()
    }

    override fun countAvailableWorkspaces(): Long {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Long::class.java)
        val root = query.from<WorkspaceModel>(WorkspaceModel::class.java)

        query.select(builder.count(root))

        return entityManager.createQuery(query).singleResult
    }

    /*override fun getAvailableWorkspaces(
        offset: Long,
        limit: Long
    ): List<WorkspaceResponse> {
        val criteriaBuilder = entityManager.criteriaBuilder
        val query = criteriaBuilder.createQuery(WorkspaceModel::class.java)
        val root = query.from(WorkspaceModel::class.java)

        query.select(root)

        // 1. Сначала получим общее количество элементов для валидации offset
        val countQuery = criteriaBuilder.createQuery(Long::class.javaObjectType)
        countQuery.select(criteriaBuilder.count(countQuery.from(WorkspaceModel::class.java)))
        val totalCount = entityManager.createQuery(countQuery).singleResult

        // 2. Проверка корректности offset
        if (offset >= totalCount) {
            return emptyList()
        }

        // 3. Создаем запрос с пагинацией
        val typedQuery = entityManager.createQuery(query).apply {
            firstResult = offset.toInt()
            maxResults = limit.toInt()
        }

        // 4. Используем resultList вместо resultStream
        return typedQuery.resultList.map { model ->
            WorkspaceResponse(
                model.workspaceId,
                model.workspaceName,
                model.description,
                model.createdDate,
               null
            )
        }
    }*/

    override fun getAvailableWorkspaces(
        offset: Long,
        limit: Long
    ): List<WorkspaceResponse> {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(WorkspaceModel::class.java)
        val root = query.from<WorkspaceModel>(WorkspaceModel::class.java)

        query.select(root)

        return entityManager.createQuery(query)
            .apply {
                firstResult = offset.toInt()
                maxResults = limit.toInt()
            }
            .resultStream
            .map { it -> WorkspaceResponse(
                it.workspaceId,
                it.workspaceName,
                it.description,
                it.createdDate,
                null
            ) }
            .toList()
    }
}