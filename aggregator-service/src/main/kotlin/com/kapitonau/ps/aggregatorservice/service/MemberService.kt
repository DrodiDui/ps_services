package com.kapitonau.ps.aggregatorservice.service

import com.kapitonau.ps.apirequestlib.aggregate.dto.member.PageableAggregateMemberResponse

interface MemberService {
    fun getAllMembers(offset: Long, limit: Long): PageableAggregateMemberResponse

}
