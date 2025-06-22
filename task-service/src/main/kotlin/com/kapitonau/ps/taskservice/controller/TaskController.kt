package com.kapitonau.ps.taskservice.controller

import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import com.kapitonau.ps.apirequestlib.tasks.api.TaskApi
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskStatusPutRequest
import com.kapitonau.ps.taskservice.service.TaskService
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController(
    private val taskService: TaskService
): TaskApi {
    override fun createTask(body: TaskPostRequest): TaskResponse =
        taskService.createTask(body)

    override fun changeTaskStatus(taskId: Long, body: TaskStatusPutRequest): TaskResponse =
        taskService.changeTaskStatus(taskId, body)
}