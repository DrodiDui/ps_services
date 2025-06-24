package com.kapitonau.ps.aggregatorservice.service.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kapitonau.ps.aggregatorservice.model.MemberModel
import com.kapitonau.ps.aggregatorservice.model.ProjectModel
import com.kapitonau.ps.aggregatorservice.repository.MemberRepository
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberModelEventDtoProcessor(
    private val memberRepository: MemberRepository,
    private val modelEventConverter: ModelEventDtoConverter
) : ModelEventDtoProcessor {
    override fun process(message: ModelEventDto) {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val projectObject = modelEventConverter.toObject(message.model)
        val project =
            objectMapper.convertValue<MemberModel>(projectObject, MemberModel::class.java)
        var existProject = memberRepository.findByIdOrNull(project.memberId!!)
        if (existProject === null) {
            memberRepository.save(project)
        } else {
            existProject.apply { existProject = project }
            memberRepository.save<MemberModel>(existProject as MemberModel)
        }
    }

    override fun getModelType(): ModelType {
       return ModelType.MEMBER
    }
}