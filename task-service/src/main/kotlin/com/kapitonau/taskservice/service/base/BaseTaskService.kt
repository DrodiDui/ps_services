package com.kapitonau.taskservice.service.base

import com.kapitonau.commonspring.exception.CommonServiceException
import com.kapitonau.projectstudio.bean.cache.ReferenceCache
import com.kapitonau.projectstudio.common.dto.EmptyDto
import com.kapitonau.projectstudio.taskservice.dto.PageableTaskResponse
import com.kapitonau.projectstudio.taskservice.dto.StatusTaskResponse
import com.kapitonau.projectstudio.taskservice.dto.TaskPostRequest
import com.kapitonau.projectstudio.taskservice.dto.TaskResponse
import com.kapitonau.projectstudio.taskservice.dto.TaskStatusPutRequest
import com.kapitonau.taskservice.model.TaskModel
import com.kapitonau.taskservice.model.mapper.TaskMapper
import com.kapitonau.taskservice.repository.TaskCounterRepository
import com.kapitonau.taskservice.repository.TaskRepository
import com.kapitonau.taskservice.service.TaskService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder.getLocale
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BaseTaskService(
    private val taskMapper: TaskMapper,
    private val taskRepository: TaskRepository,
    private val referenceCache: ReferenceCache,
    private val taskCounterRepository: TaskCounterRepository,
    private val messageSource: MessageSource
) : TaskService {

    override fun getTasks(offset: Long?, limit: Long?): PageableTaskResponse {

        val totalCount = taskRepository.countTasksByFilters()
        if (totalCount == 0.toLong()) {
            return PageableTaskResponse(
                totalCount,
                listOf(),
            )
        }

        val tasks = taskRepository.getTasksByFilters(offset, limit)
            .map { taskMapper.map(it) }
            .toList()

        return PageableTaskResponse(totalCount, tasks)
    }

    override fun getTasksByStatuses(): List<StatusTaskResponse> {

        val taskByStatusGroup = taskRepository.findAllAndGroupByStatus()
        val groupedTaskByStatus = mutableMapOf<String, MutableList<TaskResponse>>()
        for (projection in taskByStatusGroup) {
            val statusCode = referenceCache.getReferenceItemById(projection.getStatusId()).itemCode
            if (groupedTaskByStatus.containsKey(statusCode)) {
                groupedTaskByStatus.get(statusCode)?.let { task -> task.add(taskMapper.map(projection.getTaskModel())) }
            } else {
                groupedTaskByStatus.put(statusCode, mutableListOf(taskMapper.map(projection.getTaskModel())))
            }
        }
        return groupedTaskByStatus.entries.stream()
            .map { entry -> StatusTaskResponse(entry.key, entry.value) }
            .toList()

    }

    override fun getTaskById(taskId: Long): TaskResponse? =
        taskRepository.findById(taskId)
            .map { taskMapper.map(it) }
            .orElseThrow {
                CommonServiceException(
                    "TASK_SERVICE",
                    messageSource.getMessage("TASK_SERVICE_1", null, getLocale())
                )
            }

    @Transactional(transactionManager = "taskPlatformTransactionManager")
    override fun addTask(body: TaskPostRequest): TaskResponse {
        val taskCounter = taskCounterRepository.findById(body.projectId)
            .orElseThrow {
                CommonServiceException(
                    "TASK_SERVICE",
                    messageSource.getMessage("TASK_SERVICE_2", null, getLocale())
                )
            }

        var model = taskMapper.map(body);
        model.name = "TASK-${taskCounter.id}-${taskCounter.taskCounter}"
        model = taskRepository.save(model)

        taskCounter.taskCounter = taskCounter.taskCounter?.plus(1)
        taskCounterRepository.save(taskCounter)

        return taskMapper.map(model)
    }

    @Transactional(transactionManager = "taskPlatformTransactionManager")
    override fun updateTaskStatus(
        taskId: Long,
        body: TaskStatusPutRequest
    ): TaskResponse {
        var taskModel = taskRepository.findById(taskId)
            .orElseThrow {
                CommonServiceException(
                    "TASK_SERVICE",
                    messageSource.getMessage("TASK_SERVICE_1", null, getLocale())
                )
            }

        taskModel.taskStatusId =
            referenceCache.getReferenceItemByTypeAndCode("TASK_STATUS", body.statusCode).referenceItemId
        taskModel = taskRepository.save(taskModel)

        return taskMapper.map(taskModel)
    }

    override fun deleteTaskById(taskId: Long): EmptyDto {
        val optional = taskRepository.findById(taskId)
        val taskModel = optional
            .orElseThrow {
                CommonServiceException(
                    "TASK_SERVICE",
                    messageSource.getMessage("TASK_SERVICE_1", null, getLocale())
                )
            }

        if ("TODO" != referenceCache.getReferenceItemById(taskModel.taskStatusId).itemCode) {
            throw CommonServiceException(
                "TASK_SERVICE",
                messageSource.getMessage(
                    "TASK_SERVICE_3",
                    arrayOf<String>(referenceCache.getReferenceItemById(taskModel.taskStatusId).description),
                    getLocale()
                )
            )
        }

        taskRepository.delete(taskModel)

        return EmptyDto()
    }

}













