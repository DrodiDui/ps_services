package com.kapitonau.ps.aggregatorservice.controller

import com.kapitonau.ps.aggregatorservice.service.MemberService
import com.kapitonau.ps.apirequestlib.aggregate.api.AggregateMemberApi
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.AggregateMemberResponse
import com.kapitonau.ps.apirequestlib.aggregate.dto.member.PageableAggregateMemberResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class AggregateMemberController(
    private val memberService: MemberService
): AggregateMemberApi {
    override fun getAllMembers(
        offset: Long,
        limit: Long
    ): PageableAggregateMemberResponse = memberService.getAllMembers(offset, limit)

    override fun getWorkspaceMembers(workspaceId: Long?): List<AggregateMemberResponse?>? {
        TODO("Not yet implemented")
    }

    override fun getAvailableMembers(workspaceId: Long?): List<AggregateMemberResponse?>? {
        TODO("Not yet implemented")
    }


}