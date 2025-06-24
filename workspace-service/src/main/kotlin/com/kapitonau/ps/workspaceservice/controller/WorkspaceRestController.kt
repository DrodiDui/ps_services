package com.kapitonau.ps.workspaceservice.controller

import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.apirequestlib.workspaces.api.WorkspaceApi
import com.kapitonau.ps.apirequestlib.workspaces.dto.WorkspacePostRequest
import com.kapitonau.ps.workspaceservice.service.WorkspaceService
import org.springframework.web.bind.annotation.RestController

@RestController
class WorkspaceRestController(
    private val workspaceService: WorkspaceService,
): WorkspaceApi {

    override fun createWorkspace(body: WorkspacePostRequest): WorkspaceResponse {
        return workspaceService.createWorkspace(body)
    }

    override fun getWorkspace(workspaceId: Long): WorkspaceResponse {
        return workspaceService.getWorkspaceById(workspaceId)
    }
}