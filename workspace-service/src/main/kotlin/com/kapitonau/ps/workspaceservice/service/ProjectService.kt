package com.kapitonau.ps.workspaceservice.service

import com.kapitonau.ps.apirequestlib.common.EmptyDto
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import com.kapitonau.ps.apirequestlib.workspaces.dto.ProjectPostRequest

interface ProjectService {
    fun createProject(body: ProjectPostRequest): ProjectResponse
    fun deleteProject(projectId: Long): EmptyDto
    fun getProjectById(projectId: Long): ProjectResponse
}
