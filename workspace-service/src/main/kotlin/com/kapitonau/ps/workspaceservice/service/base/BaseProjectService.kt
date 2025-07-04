package com.kapitonau.ps.workspaceservice.service.base

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.EmptyDto
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.kafka.ProjectCreateEventMessage
import com.kapitonau.ps.apirequestlib.workspaces.dto.ProjectPostRequest
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
import com.kapitonau.ps.workspaceservice.event.sender.ModelEventSender
import com.kapitonau.ps.workspaceservice.event.sender.ProjectCreateEventSender
import com.kapitonau.ps.workspaceservice.model.ProjectModel
import com.kapitonau.ps.workspaceservice.repository.ProjectRepository
import com.kapitonau.ps.workspaceservice.service.ProjectService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class BaseProjectService(
    private val projectRepository: ProjectRepository,
    private val modelEventService: ModelEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter,
    private val referenceCache: ReferenceCache,
    private val projectCreateEventSender: ProjectCreateEventSender
) : ProjectService {

    @Transactional(transactionManager = "workspaceTransactionManager", rollbackFor = [Exception::class])
    override fun createProject(body: ProjectPostRequest): ProjectResponse {

        projectRepository.findByProjectTitle(body.projectName).ifPresent {
            throw CommonServiceException("WORKSPACE_SERVICE", "Project name already exists")
        }

        var projectModel = ProjectModel()
        projectModel.projectTitle = body.projectName
        projectModel.description = body.description
        projectModel.workspaceId = body.workspaceId
        projectModel.projectTypeId = referenceCache.getReferenceItemByTypeAndCode("PROJECT_TYPE", body.projectTypeCode).referenceItemId
        projectModel.isDeleted = false

        projectModel = projectRepository.save(projectModel)

        modelEventService.send(ModelEventDto(
            ModelType.PROJECT_MODEL,
            modelEventDtoConverter.toMap(projectModel)
        ))

        projectCreateEventSender.send(ProjectCreateEventMessage(projectModel.projectId))

        return ProjectResponse(
            projectModel.projectId,
            projectModel.projectTitle,
            projectModel.description,
            projectModel.createdDate,
            referenceCache.getReferenceItemById(projectModel.projectTypeId)
        )
    }

    @Transactional(transactionManager = "workspaceTransactionManager", rollbackFor = [Exception::class])
    override fun deleteProject(projectId: Long): EmptyDto {

        val project = projectRepository.findById(projectId)
            .orElseThrow { CommonServiceException("PROJECT_SERVICE", "Project not found") }

        project.isDeleted = true
        projectRepository.save(project)

        val modelMap = modelEventDtoConverter.toMap(project)
        modelEventService.send(ModelEventDto(
            ModelType.PROJECT_MODEL,
            modelMap
        ))

        return EmptyDto()
    }
}
