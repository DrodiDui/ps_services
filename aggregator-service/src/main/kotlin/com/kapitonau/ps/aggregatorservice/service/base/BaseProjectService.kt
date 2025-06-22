package com.kapitonau.ps.aggregatorservice.service.base

import com.kapitonau.ps.aggregatorservice.repository.ProjectRepository
import com.kapitonau.ps.aggregatorservice.service.ProjectService
import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import org.springframework.stereotype.Service

@Service
class BaseProjectService(
    private val projectRepository: ProjectRepository,
    private val referenceCache: ReferenceCache
) : ProjectService {

    override fun getAvailableProjects(workspaceId: Long): List<ProjectResponse> {
        return projectRepository.findAllByWorkspaceIdAndIsDeleted(workspaceId, false)
            .map { it ->
                ProjectResponse(
                    it.projectId,
                    it.projectTitle,
                    it.description,
                    it.createdDate,
                    referenceCache.getReferenceItemById(it.projectTypeId)
                )
             }
            .toList()
    }
}