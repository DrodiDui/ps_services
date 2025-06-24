package com.kapitonau.ps.workspaceservice.service

import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse

interface MemberService {
    fun getMemberById(memberId: Long): MemberResponse
}