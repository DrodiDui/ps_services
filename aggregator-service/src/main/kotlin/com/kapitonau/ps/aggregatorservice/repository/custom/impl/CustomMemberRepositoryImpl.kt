package com.kapitonau.ps.aggregatorservice.repository.custom.impl

import com.kapitonau.ps.aggregatorservice.model.MemberModel
import com.kapitonau.ps.aggregatorservice.repository.custom.CustomMemberRepository
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.AggregateMemberResponse
import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
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
}