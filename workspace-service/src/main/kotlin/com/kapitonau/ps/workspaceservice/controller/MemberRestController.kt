package com.kapitonau.ps.workspaceservice.controller

import com.kapitonau.ps.apirequestlib.workspaces.api.MemberApi
import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse
import com.kapitonau.ps.workspaceservice.service.MemberService
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
    private val memberService: MemberService

) : MemberApi {
    override fun getMember(memberId: Long): MemberResponse =
        memberService.getMemberById(memberId)


}
