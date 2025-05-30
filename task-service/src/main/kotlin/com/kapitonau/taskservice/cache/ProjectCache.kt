package com.kapitonau.taskservice.cache

import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse
import com.kapitonau.taskservice.client.ProjectApiClient
import org.springframework.stereotype.Component

@Component
class ProjectCache(
    private val projectApiClient: ProjectApiClient
) {

    private val PROJECT_CACHE = mutableMapOf<Long, ProjectResponse>()

    fun getProject(projectId: Long): ProjectResponse {
        if (PROJECT_CACHE.containsKey(projectId)) {
            return PROJECT_CACHE[projectId]!!
        } else {
            val project = projectApiClient.getProject(projectId)
            PROJECT_CACHE.put(projectId, project)
            return project
        }
    }

}