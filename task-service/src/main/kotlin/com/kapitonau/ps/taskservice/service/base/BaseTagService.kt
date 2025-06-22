package com.kapitonau.ps.taskservice.service.base

import com.kapitonau.ps.apirequestlib.common.tasks.TagResponse
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.tasks.dto.TagBatchPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TagPostRequest
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
import com.kapitonau.ps.taskservice.event.sender.ModelEventSender
import com.kapitonau.ps.taskservice.model.TagModel
import com.kapitonau.ps.taskservice.repository.TagRepository
import com.kapitonau.ps.taskservice.service.TagService
import org.springframework.stereotype.Service

@Service
class BaseTagService(
    private val tagRepository: TagRepository,
    private val modelEventSender: ModelEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter
) : TagService {

    override fun createTag(body: TagPostRequest): TagResponse {
        tagRepository.findByWorkspaceIdAndTagName(body.workspaceId, body.tagName)
            .ifPresent { throw CommonServiceException("TASKS_SERVICE", "Tag already exist in workspace") }

        var model = TagModel()
        model.tagName = body.tagName
        model.workspaceId = body.workspaceId

        model = tagRepository.save(model)

        modelEventSender.send(ModelEventDto(
            ModelType.TAG_MODEL,
            modelEventDtoConverter.toMap(model)
        ))

        return TagResponse(model.tagId, model.tagName, model.workspaceId)
    }

    override fun createTagBatch(body: TagBatchPostRequest): List<TagResponse> {
        TODO("Not yet implemented")
    }
}