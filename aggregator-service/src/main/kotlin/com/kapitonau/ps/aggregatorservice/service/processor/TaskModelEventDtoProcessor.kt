package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.ProjectModel
import com.kapitonau.ps.aggregatorservice.model.TaskModel
import com.kapitonau.ps.aggregatorservice.repository.TaskRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TaskModelEventDtoProcessor(
    private val eventModelEventDtoConverter: ModelEventDtoConverter,
    private val taskRepository: TaskRepository
) : ModelEventDtoProcessor {

    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val projectObject = eventModelEventDtoConverter.toObject(message.model)
        val project =
            objectMapper.convertValue<TaskModel>(projectObject, TaskModel::class.java)
        var existProject = taskRepository.findByIdOrNull(project.taskId!!)
        if (existProject === null) {
            taskRepository.save(project)
        } else {
            existProject.apply { existProject = project }
            taskRepository.save<TaskModel>(existProject as TaskModel)
        }
    }

    override fun getModelType(): ModelType {
        return ModelType.TASKS_MODEL
    }
}