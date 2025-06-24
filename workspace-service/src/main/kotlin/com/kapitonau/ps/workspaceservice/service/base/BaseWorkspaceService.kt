package com.kapitonau.ps.workspaceservice.service.base

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.workspaces.dto.WorkspacePostRequest
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
import com.kapitonau.ps.workspaceservice.event.sender.ModelEventSender
import com.kapitonau.ps.workspaceservice.model.mapper.WorkspaceMapper
import com.kapitonau.ps.workspaceservice.repository.WorkspaceRepository
import com.kapitonau.ps.workspaceservice.service.WorkspaceService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder.getLocale
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BaseWorkspaceService(
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceMapper: WorkspaceMapper,
    private val modelEventSender: ModelEventSender,
    private val messageSource: MessageSource,
    private val eventDtoConverter: ModelEventDtoConverter
) : WorkspaceService {

    @Transactional(transactionManager = "workspaceTransactionManager", rollbackFor = [Exception::class])
    override fun createWorkspace(body: WorkspacePostRequest): WorkspaceResponse {

        workspaceRepository.findByWorkspaceName(body.workspaceName)
            .ifPresent {
                throw CommonServiceException(
                    "WORKSPACE_ERROR",
                    messageSource.getMessage("WORKSPACE_1", arrayOf(body.workspaceName), getLocale())
                )
            }

        var model = workspaceMapper.toModel(body)
        model = workspaceRepository.save(model)

        modelEventSender.send(
            ModelEventDto(
                ModelType.WORKSPACE_MODEL,
                eventDtoConverter.toMap(model)
            )
        )

        return workspaceMapper.toDto(model)
    }

    override fun createWorkspaceWithOwner(
        body: WorkspacePostRequest,
        ownerId: Long
    ): WorkspaceResponse {
        workspaceRepository.findByWorkspaceName(body.workspaceName)
            .ifPresent {
                throw CommonServiceException(
                    "WORKSPACE_ERROR",
                    messageSource.getMessage("WORKSPACE_1", arrayOf(body.workspaceName), getLocale())
                )
            }

        var model = workspaceMapper.toModel(body, ownerId)
        model = workspaceRepository.save(model)

        modelEventSender.send(
            ModelEventDto(
                ModelType.WORKSPACE_MODEL,
                eventDtoConverter.toMap(model)
            )
        )

        return workspaceMapper.toDto(model)
    }

    override fun getWorkspaceById(workspaceId: Long): WorkspaceResponse {
        return workspaceRepository.findById(workspaceId)
            .map { it -> workspaceMapper.toDto(it) }
            .orElseThrow { CommonServiceException("WORKSPACE_SERVICE", "Workspace not found") }
    }


}