package com.kapitonau.ps.taskservice.cache

import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.taskservice.client.WorkspaceApiClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class WorkspaceCache(
    private val workspaceApiClient: WorkspaceApiClient
) {


    @Cacheable(value = ["workspaces"], key = "#workspaceId")
    fun getWorkspaceById(workspaceId: Long): WorkspaceResponse {
        return workspaceApiClient.getWorkspace(workspaceId)
    }
}