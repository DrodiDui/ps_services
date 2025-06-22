package com.kapitonau.ps.workspaceservice.controller

import com.kapitonau.ps.apirequestlib.common.EmptyDto
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import com.kapitonau.ps.apirequestlib.workspaces.api.ProjectApi
import com.kapitonau.ps.apirequestlib.workspaces.dto.ProjectPostRequest
import com.kapitonau.ps.workspaceservice.service.ProjectService
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectRestController(
    private val projectService: ProjectService
) : ProjectApi {

    override fun createProject(body: ProjectPostRequest): ProjectResponse {
        return projectService.createProject(body)
    }

    override fun deleteProject(projectId: Long): EmptyDto {
        return projectService.deleteProject(projectId)
    }
}
