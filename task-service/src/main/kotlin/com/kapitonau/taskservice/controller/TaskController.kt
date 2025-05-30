package com.kapitonau.taskservice.controller

import com.kapitonau.projectstudio.common.dto.EmptyDto
import com.kapitonau.projectstudio.taskservice.api.TaskApi
import com.kapitonau.projectstudio.taskservice.dto.PageableTaskResponse
import com.kapitonau.projectstudio.taskservice.dto.StatusTaskResponse
import com.kapitonau.projectstudio.taskservice.dto.TaskPostRequest
import com.kapitonau.projectstudio.taskservice.dto.TaskResponse
import com.kapitonau.projectstudio.taskservice.dto.TaskStatusPutRequest
import com.kapitonau.taskservice.service.TaskService
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController(
    private val taskService: TaskService
) : TaskApi {


    override fun getTasks(
        offset: Long?,
        limit: Long?
    ): PageableTaskResponse = taskService.getTasks(offset, limit)

    override fun getTasksByStatues(): List<StatusTaskResponse?>? =
        taskService.getTasksByStatuses()

    override fun getTask(taskId: Long): TaskResponse? =
        taskService.getTaskById(taskId)

    override fun addTask(body: TaskPostRequest): TaskResponse =
        taskService.addTask(body);

    override fun updateTask(
        taskId: Long,
        body: TaskStatusPutRequest
    ): TaskResponse = taskService.updateTaskStatus(taskId, body)

    override fun deleteTask(taskId: Long): EmptyDto =
        taskService.deleteTaskById(taskId);

}