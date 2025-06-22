package com.kapitonau.ps.taskservice.service.base

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.tasks.TagResponse
import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskStatusPutRequest
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
import com.kapitonau.ps.commonspringlib.security.ResourceSecurityUtil
import com.kapitonau.ps.taskservice.event.sender.ModelEventSender
import com.kapitonau.ps.taskservice.model.ProjectTaskCounterModel
import com.kapitonau.ps.taskservice.model.TaskModel
import com.kapitonau.ps.taskservice.model.TaskTagModel
import com.kapitonau.ps.taskservice.model.TaskTagModelId
import com.kapitonau.ps.taskservice.repository.ProjectTaskCounterRepository
import com.kapitonau.ps.taskservice.repository.TagRepository
import com.kapitonau.ps.taskservice.repository.TaskRepository
import com.kapitonau.ps.taskservice.repository.TaskTagRepository
import com.kapitonau.ps.taskservice.service.TaskService
import com.kapitonau.ps.taskservice.service.TaskTagsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import kotlin.math.min

@Service
class BaseTaskService(
    private val taskRepository: TaskRepository,
    private val projectTaskCounterRepository: ProjectTaskCounterRepository,
    private val referenceCache: ReferenceCache,
    private val taskTagService: TaskTagsService,
    private val modelEventSender: ModelEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter
) : TaskService {


    @Transactional(transactionManager = "taskTransactionManager")
    override fun createTask(body: TaskPostRequest): TaskResponse {

        val currentUser = ResourceSecurityUtil.getUserId()

        val counter = projectTaskCounterRepository.findById(body.projectId)
            .orElseThrow { CommonServiceException("TASKS_SERVICE", "Project counter not found") }

        var model = TaskModel()
        model.taskName = "TASK-${body.workspaceId}${counter.projectId}-${counter.counter}"
        model.taskTitle = body.title
        model.description = body.description
        model.ownerId = currentUser
        if (body.assigneeId == null) {
            model.assigneeId = currentUser
        } else {
            model.assigneeId = body.assigneeId
        }
        model.taskStatusId =
            referenceCache.getReferenceItemByTypeAndCode("TASK_STATUS", body.taskStatusCode).referenceItemId
        model.taskPriorityId =
            referenceCache.getReferenceItemByTypeAndCode("TASK_PRIORITY", body.taskPriorityCode).referenceItemId
        model.createdDate = ZonedDateTime.now()
        model.createdBy = currentUser
        model.lastModifiedDate = ZonedDateTime.now()
        model.lastModifiedBy = currentUser
        model.projectId = body.projectId
        model.taskTypeId = referenceCache.getReferenceItemByTypeAndCode("TASK_TYPE", body.taskTypeCode).referenceItemId
        model.workspaceId = body.workspaceId

        model = taskRepository.save<TaskModel>(model)

        if (!body.tagIds.isEmpty()) {
            model.taskId?.let { taskTagService.addTagsToTask(it, body.tagIds, body.workspaceId) }
        }

        counter.counter = counter.counter?.plus(1)
        projectTaskCounterRepository.save<ProjectTaskCounterModel>(counter)

        modelEventSender.send(ModelEventDto(
            ModelType.TASKS_MODEL,
            modelEventDtoConverter.toMap(model)
        ))

        return TaskResponse(
            model.taskId,
            model.taskName,
            model.taskTitle,
            model.description,
            referenceCache.getReferenceItemById(model.taskStatusId),
            referenceCache.getReferenceItemById(model.taskPriorityId),
            referenceCache.getReferenceItemById(model.taskTypeId),
            null,
            null,
            null,
            null,
            model.createdDate,
            model.lastModifiedDate,
            listOf<TagResponse>()
        )
    }

    @Transactional(transactionManager = "taskTransactionManager")
    override fun changeTaskStatus(
        taskId: Long,
        body: TaskStatusPutRequest
    ): TaskResponse {
        val task = taskRepository.findById(taskId)
            .orElseThrow { CommonServiceException("TASKS_SERVICE", "Task not found") }

        task.taskStatusId = referenceCache.getReferenceItemByTypeAndCode("TASK_STATUS", body.statusCode)
            .referenceItemId

        taskRepository.save(task)


        modelEventSender.send(ModelEventDto(
            ModelType.TASKS_MODEL,
            modelEventDtoConverter.toMap(task)
        ))

        return TaskResponse(
            task.taskId,
            task.taskName,
            task.taskTitle,
            task.description,
            referenceCache.getReferenceItemById(task.taskStatusId),
            referenceCache.getReferenceItemById(task.taskPriorityId),
            referenceCache.getReferenceItemById(task.taskTypeId),
            null,
            null,
            null,
            null,
            task.createdDate,
            task.lastModifiedDate,
            listOf<TagResponse>()
        )
    }
}


















