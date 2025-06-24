package com.kapitonau.ps.aggregatorservice.repository.custom

import com.kapitonau.ps.apirequestlib.aggregate.dto.member.AggregateMemberResponse

interface CustomMemberRepository {

    fun countMembers(): Long

    fun getAllMembers(offset: Int, limit: Int): List<AggregateMemberResponse>

    fun getAllAvailableMembersByWorkspaceId(workspaceId: Long?): List<AggregateMemberResponse>

}
