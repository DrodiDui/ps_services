package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.TaskTagModel
import com.kapitonau.ps.aggregatorservice.model.TaskTagModelId
import com.kapitonau.ps.aggregatorservice.repository.TaskTagRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TaskTagModelEventDtoProcessor(
    private val modelEventDtoConverter: ModelEventDtoConverter,
    private val taskTagRepository: TaskTagRepository,
) : ModelEventDtoProcessor {

    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val projectObject = modelEventDtoConverter.toObject(message.model)
        val project =
            objectMapper.convertValue<TaskTagModel>(projectObject, TaskTagModel::class.java)
        val taskTagModelId = project.taskTagId!!
        var existProject = taskTagRepository.findByIdOrNull(taskTagModelId)
        if (existProject === null) {
            taskTagRepository.save(project)
        } else {
            existProject.apply { existProject = project }
            taskTagRepository.save<TaskTagModel>(existProject as TaskTagModel)
        }
    }

    override fun getModelType(): ModelType {
        return ModelType.TASK_TAG_MODEL
    }
}