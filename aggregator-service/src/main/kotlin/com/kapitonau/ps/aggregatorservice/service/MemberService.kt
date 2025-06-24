package com.kapitonau.ps.aggregatorservice.service

import com.kapitonau.ps.apirequestlib.aggregate.dto.member.AggregateMemberResponse
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.PageableAggregateMemberResponse

interface MemberService {
    fun getAllMembers(offset: Long, limit: Long): PageableAggregateMemberResponse
    fun getAvailableMembers(workspaceId: Long?): List<AggregateMemberResponse?>?

}
