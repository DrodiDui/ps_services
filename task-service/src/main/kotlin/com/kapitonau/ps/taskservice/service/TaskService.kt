package com.kapitonau.ps.taskservice.service

import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskStatusPutRequest

interface TaskService {
    fun createTask(body: TaskPostRequest): TaskResponse
    fun changeTaskStatus(taskId: Long, body: TaskStatusPutRequest): TaskResponse

}
