package com.kapitonau.taskservice.repository.custom

import com.kapitonau.projectstudio.taskservice.dto.StatusTaskResponse
import com.kapitonau.taskservice.model.TaskModel

interface CustomTaskRepository {

    fun countTasksByFilters(): Long

    fun getTasksByFilters(offset: Long?, limit: Long?): List<TaskModel>

    fun getTaskByStatus(): List<StatusTaskResponse>

}
