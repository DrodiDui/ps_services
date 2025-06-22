package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.ProjectModel
import com.kapitonau.ps.aggregatorservice.model.WorkspaceModel
import com.kapitonau.ps.aggregatorservice.repository.ProjectRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProjectModelEventDtoProcessor(
    private val projectRepository: ProjectRepository,
    private val eventDtoConverter: ModelEventDtoConverter
) : ModelEventDtoProcessor {

    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val projectObject = eventDtoConverter.toObject(message.model)
        val project =
            objectMapper.convertValue<ProjectModel>(projectObject, ProjectModel::class.java)
        var existProject = projectRepository.findByIdOrNull(project.projectId!!)
        if (existProject === null) {
            projectRepository.save(project)
        } else {
            existProject.apply { existProject = project }
            projectRepository.save<ProjectModel>(existProject as ProjectModel)
        }

    }

    override fun getModelType(): ModelType {
        return ModelType.PROJECT_MODEL
    }
}