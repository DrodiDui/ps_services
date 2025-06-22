package com.kapitonau.ps.aggregatorservice.repository.custom

import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse

interface CustomWorkspaceRepository {

    fun countAvailableMemberWorkspace(ownerId: Long): Long
    fun getAvailableMemberWorkspace(offset: Long, limit: Long, ownerId: Long): List<WorkspaceResponse>

    fun countAvailableWorkspaces(): Long
    fun getAvailableWorkspaces(offset: Long, limit: Long): List<WorkspaceResponse>

}
