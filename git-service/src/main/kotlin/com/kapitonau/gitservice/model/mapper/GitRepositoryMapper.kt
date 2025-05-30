package com.kapitonau.gitservice.model.mapper

import com.kapitonau.gitservice.cache.CacheProvider
import com.kapitonau.gitservice.model.GitRepositoryModel
import com.kapitonau.projectstudio.bean.cache.ReferenceCache
import com.kapitonau.projectstudio.gitservice.dto.RepositoryResponse
import org.springframework.stereotype.Component

@Component
class GitRepositoryMapper(
    private val cacheProvider: CacheProvider,
    private val referenceCache: ReferenceCache
) {

    fun map(model: GitRepositoryModel): RepositoryResponse {
        val response = RepositoryResponse()
        response.repositoryId = model.repositoryId
        response.name = model.repositoryName
        model.projectId?.let { response.project = cacheProvider.getProject(it) }
        model.ownerId?.let { response.owner = cacheProvider.getEmployee(it) }
        response.lastModifiedTime = model.modifiedTime
        model.modifiedBy?.let { response.lastModifiedBy = cacheProvider.getEmployee(it) }
        response.gitProvider = referenceCache.getReferenceItemById(model.gitProviderId)

        return response
    }

}
