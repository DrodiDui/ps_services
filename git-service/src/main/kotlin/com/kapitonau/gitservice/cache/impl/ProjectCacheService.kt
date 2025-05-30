package com.kapitonau.gitservice.cache.impl

import com.kapitonau.gitservice.cache.BaseCacheService
import com.kapitonau.gitservice.client.feign.ProjectApiClient
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse
import org.springframework.stereotype.Service

@Service
class ProjectCacheService(
    private val projectApiClient: ProjectApiClient
) : BaseCacheService<ProjectResponse> {

    private val cache: MutableMap<String, ProjectResponse> = HashMap()

    override fun getByEntityId(entityId: Long): ProjectResponse {
        if (cache.containsKey(entityId.toString())) {
            return cache[entityId.toString()]!!
        } else {
            val response = projectApiClient.getProject(entityId)
            cache.put(entityId.toString(), response)
            return response
        }
    }


}