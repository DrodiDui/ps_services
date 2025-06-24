package com.kapitonau.ps.taskservice.service.base

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.EmptyDto
import com.kapitonau.ps.apirequestlib.common.tasks.TagResponse
import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskStatusPutRequest
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
import com.kapitonau.ps.taskservice.event.sender.ModelEventSender
import com.kapitonau.ps.taskservice.model.ProjectTaskCounterModel
import com.kapitonau.ps.taskservice.model.TaskModel
import com.kapitonau.ps.taskservice.model.mapper.TaskMapper
import com.kapitonau.ps.taskservice.repository.ProjectTaskCounterRepository
import com.kapitonau.ps.taskservice.repository.TaskRepository
import com.kapitonau.ps.taskservice.service.TaskService
import com.kapitonau.ps.taskservice.service.TaskTagsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BaseTaskService(
    private val taskRepository: TaskRepository,
    private val projectTaskCounterRepository: ProjectTaskCounterRepository,
    private val referenceCache: ReferenceCache,
    private val taskTagService: TaskTagsService,
    private val modelEventSender: ModelEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter,
    private val taskMapper: TaskMapper,
) : TaskService {


    @Transactional(transactionManager = "taskTransactionManager")
    override fun createTask(body: TaskPostRequest): TaskResponse {

        val counter = projectTaskCounterRepository.findById(body.projectId)
            .orElseThrow { CommonServiceException("TASKS_SERVICE", "Project counter not found") }

        var model = taskMapper.toModel(body)
        model.taskName = "TASK-${body.workspaceId}${counter.projectId}-${counter.counter}"

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

        return taskMapper.toResponse(model)
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

        return taskMapper.toResponse(task)
    }

    @Transactional(transactionManager = "taskTransactionManager")
    override fun deleteTask(taskId: Long): EmptyDto {
        val task = taskRepository.findById(taskId)
            .orElseThrow { CommonServiceException("TASKS_SERVICE", "Task not found") }
        taskTagService.deleteByTaskId(taskId)

        taskRepository.delete(task)
        return EmptyDto()
    }
}


















