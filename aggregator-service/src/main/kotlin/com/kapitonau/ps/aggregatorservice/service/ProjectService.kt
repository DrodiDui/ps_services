package com.kapitonau.ps.aggregatorservice.service

import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse

interface ProjectService {
    fun getAvailableProjects(workspaceId: Long): List<ProjectResponse>

}
