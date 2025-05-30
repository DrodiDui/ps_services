package com.kapitonau.taskservice.service

import com.kapitonau.projectstudio.common.dto.EmptyDto
import com.kapitonau.projectstudio.taskservice.dto.PageableTaskResponse
import com.kapitonau.projectstudio.taskservice.dto.StatusTaskResponse
import com.kapitonau.projectstudio.taskservice.dto.TaskPostRequest
import com.kapitonau.projectstudio.taskservice.dto.TaskResponse
import com.kapitonau.projectstudio.taskservice.dto.TaskStatusPutRequest

interface TaskService {
    fun getTasks(offset: Long?, limit: Long?): PageableTaskResponse
    fun getTasksByStatuses(): List<StatusTaskResponse>
    fun getTaskById(taskId: Long): TaskResponse?
    fun addTask(body: TaskPostRequest): TaskResponse
    fun updateTaskStatus(taskId: Long, body: TaskStatusPutRequest): TaskResponse
    fun deleteTaskById(taskId: Long): EmptyDto
}

