package com.kapitonau.ps.taskservice.cache

import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import com.kapitonau.ps.taskservice.client.ProjectApiClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class ProjectCache(
    private val projectApiClient: ProjectApiClient
) {

    @Cacheable(value = ["projects"], key = "#projectId")
    fun getProjectById(projectId: Long): ProjectResponse {
        return projectApiClient.getProject(projectId)
    }
}