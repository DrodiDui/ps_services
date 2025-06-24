package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.TaskTagModel
import com.kapitonau.ps.aggregatorservice.model.WorkspaceMemberModel
import com.kapitonau.ps.aggregatorservice.repository.WorkspaceMemberRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class WorkspaceMemberModelEventDtoProcessor(
    private val workspaceMemberRepository: WorkspaceMemberRepository,
    private val modelEventDtoConverter: ModelEventDtoConverter
) : ModelEventDtoProcessor {
    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val workspaceMemberObject = modelEventDtoConverter.toObject(message.model)
        val workspaceMember =
            objectMapper.convertValue<WorkspaceMemberModel>(workspaceMemberObject, WorkspaceMemberModel::class.java)

        workspaceMemberRepository.save(workspaceMember)
    }

    override fun getModelType(): ModelType {
        return ModelType.WORKSPACE_MEMBER
    }
}