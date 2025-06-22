package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.ProjectModel
import com.kapitonau.ps.aggregatorservice.model.TagModel
import com.kapitonau.ps.aggregatorservice.model.TaskModel
import com.kapitonau.ps.aggregatorservice.repository.TagRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TagModelEventDtoProcessor(
    private val modelEventDtoConverter: ModelEventDtoConverter,
    private val tagRepository: TagRepository
) : ModelEventDtoProcessor {
    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val projectObject = modelEventDtoConverter.toObject(message.model)
        val project =
            objectMapper.convertValue<TagModel>(projectObject, TagModel::class.java)
        var existProject = tagRepository.findByIdOrNull(project.tagId!!)
        if (existProject === null) {
            tagRepository.save(project)
        } else {
            existProject.apply { existProject = project }
            tagRepository.save<TagModel>(existProject as TagModel)
        }
    }

    override fun getModelType(): ModelType {
        return ModelType.TAG_MODEL
    }
}