package com.kapitonau.ps.aggregatorservice.repository.custom.impl

import com.kapitonau.ps.aggregatorservice.model.MemberModel
import com.kapitonau.ps.aggregatorservice.repository.custom.CustomMemberRepository
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.AggregateMemberResponse
import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import kotlin.streams.toList

@Repository
class CustomMemberRepositoryImpl(
    private val referenceCache: ReferenceCache,
    private @PersistenceContext val entityManager: EntityManager
) : CustomMemberRepository {


    override fun countMembers(): Long {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(Long::class.java)
        val from = query.from<MemberModel>(MemberModel::class.java)

        query.select(builder.count(from))

        return entityManager.createQuery(query).singleResult
    }


    override fun getAllMembers(
        offset: Int,
        limit: Int
    ): List<AggregateMemberResponse> {

        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(MemberModel::class.java)


        val toList = entityManager.createNamedQuery(
            """
                """.trimIndent()
        ).apply {
            firstResult = offset
            maxResults = limit
        }
            .resultStream
            .toList()


        TODO("")
    }

    override fun getAllAvailableMembersByWorkspaceId(workspaceId: Long?): List<AggregateMemberResponse> {
        val query = """
            select 
                m.member_id, 
                m.username, 
                m.email, 
                m.first_name, 
                m.last_name, 
                m.middle_name, 
                m.member_role_id, 
                m.member_status_id 
            from members m 
                join workspace_members wm on wm.member_id = m.member_id 
            where wm.workspace_id = :workspaceId
        """.trimIndent()

        val nativeQuery = entityManager.createNativeQuery(query)
        nativeQuery.setParameter("workspaceId", workspaceId)

        val results = nativeQuery.resultList as List<Array<Any>>
        return results.map { row -> mapRowToTaskResponse(row) }
    }

    private fun mapRowToTaskResponse(row: Array<Any>): AggregateMemberResponse {
        return AggregateMemberResponse(
            row[0] as Long,
            row[1] as String,
            row[2] as String,
            row[3] as String,
            row[4] as String,
            if(row[5] !== null)  row[5] as String else null ,
            null,
            referenceCache.getReferenceItemById(row[6] as Long),
            referenceCache.getReferenceItemById(row[7] as Long),
            null
        )
    }
}