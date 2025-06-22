package com.kapitonau.ps.workspaceservice.service

import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.apirequestlib.workspaces.dto.WorkspacePostRequest

interface WorkspaceService {

    fun createWorkspace(body: WorkspacePostRequest): WorkspaceResponse

}