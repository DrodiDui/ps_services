package com.kapitonau.ps.aggregatorservice.service

import com.kapitonau.ps.aggregatorservice.repository.MemberRepository
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.AggregateMemberResponse
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.PageableAggregateMemberResponse
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class AggregateMemberService(
    private val memberRepository: MemberRepository
) : MemberService {
    override fun getAllMembers(
        offset: Long,
        limit: Long
    ): PageableAggregateMemberResponse {
        TODO("Not yet implemented")
    }

    override fun getAvailableMembers(workspaceId: Long?): List<AggregateMemberResponse?>? {
        return memberRepository.getAllAvailableMembersByWorkspaceId(workspaceId)
    }
}