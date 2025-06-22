package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.WorkspaceModel
import com.kapitonau.ps.aggregatorservice.repository.WorkspaceRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class WorkspaceModelEventDtoProcessor(
    private val workspaceRepository: WorkspaceRepository,
    private val modelEventConverter: ModelEventDtoConverter,
) : ModelEventDtoProcessor {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val workspaceObject = modelEventConverter.toObject(message.model)
        val workspace =
            objectMapper.convertValue<WorkspaceModel>(workspaceObject, WorkspaceModel::class.java)
        val existWorkspace = workspaceRepository.findByIdOrNull(workspace.workspaceId!!)
        if (existWorkspace === null) {
            workspaceRepository.save(workspace)
        } else {
            existWorkspace.let { existWorkspace ->
                {
                    existWorkspace.workspaceName = workspace.workspaceName
                    existWorkspace.description = workspace.description
                    existWorkspace.ownerId = workspace.ownerId
                    existWorkspace.createdDate = workspace.createdDate
                    existWorkspace.createdBy = workspace.createdBy
                    existWorkspace.lastModifiedDate = workspace.lastModifiedDate
                    existWorkspace.lastModifiedBy = workspace.lastModifiedBy
                    existWorkspace.version = workspace.version
                    existWorkspace.isDeleted = workspace.isDeleted
                }
            }
        }
    }

    override fun getModelType(): ModelType {
        return ModelType.WORKSPACE_MODEL
    }
}