package com.kapitonau.ps.workspaceservice.service

interface WorkspaceMemberService {
    fun createMaintainer(memberId: Long, workspaceId: Long, roleId: Long)
}