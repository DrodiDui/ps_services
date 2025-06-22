package com.kapitonau.ps.taskservice.service.base

import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.taskservice.event.sender.ModelEventSender
import com.kapitonau.ps.taskservice.model.TaskTagModel
import com.kapitonau.ps.taskservice.model.TaskTagModelId
import com.kapitonau.ps.taskservice.repository.TagRepository
import com.kapitonau.ps.taskservice.repository.TaskTagRepository
import com.kapitonau.ps.taskservice.service.TaskTagsService
import org.springframework.stereotype.Service

@Service
class BaseTaskTagsService(
    private val tagRepository: TagRepository,
    private val taskTagRepository: TaskTagRepository,
    private val modelEventSender: ModelEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter
) : TaskTagsService {
    
    override fun addTagsToTask(taskId: Long, tags: List<Long>, workspaceId: Long) {

        val tags = tagRepository.findAllByTagIdInAndWorkspaceId(tags, workspaceId)
            .map { it.tagId }
            .toList()

        val taskTags = mutableListOf<TaskTagModel>()
        for (tag in tags) {
            val id = TaskTagModelId()
            id.taskId = taskId
            id.tagId = tag
            val taskTagModel = TaskTagModel()
            taskTagModel.taskTagId = id
            taskTags.add(taskTagModel)
        }
        val allTags = taskTagRepository.saveAll<TaskTagModel>(taskTags)

        modelEventSender.send(
            ModelEventDto(
                ModelType.TASK_TAG_MODEL,
                modelEventDtoConverter.toMap(allTags)
            )
        )

    }
    
}