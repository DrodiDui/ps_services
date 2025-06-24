package com.kapitonau.ps.aggregatorservice.service.base

import com.kapitonau.ps.aggregatorservice.repository.ProjectRepository
import com.kapitonau.ps.aggregatorservice.service.ProjectService
import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
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
                    referenceCache.getReferenceItemById(it.projectTypeId),
                    if (it.gitProviderId != null) referenceCache.getReferenceItemById(it.gitProviderId) else null,
                )
             }
            .toList()
    }

    override fun getProjectByWorkspace(
        workspaceId: Long,
        projectId: Long
    ): ProjectResponse {

        val project = projectRepository.findByWorkspaceIdAndProjectId(workspaceId, projectId)
            .orElseThrow { CommonServiceException("AGGREGATOR_SERVICE", "Project not found") }

        return ProjectResponse(
            project.projectId,
            project.projectTitle,
            project.description,
            project.createdDate,
            referenceCache.getReferenceItemById(project.projectTypeId),
            if (project.gitProviderId != null) referenceCache.getReferenceItemById(project.gitProviderId) else null,
        )
    }
}