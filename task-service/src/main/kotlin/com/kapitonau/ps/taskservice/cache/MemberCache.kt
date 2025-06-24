package com.kapitonau.ps.taskservice.cache

import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse
import com.kapitonau.ps.taskservice.client.MemberApiClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class MemberCache(
    private val memberApiClient: MemberApiClient
) {

    @Cacheable(value = ["members"], key = "#memberId")
    fun getMemberById(memberId: Long): MemberResponse {
        return memberApiClient.getMember(memberId)
    }

}