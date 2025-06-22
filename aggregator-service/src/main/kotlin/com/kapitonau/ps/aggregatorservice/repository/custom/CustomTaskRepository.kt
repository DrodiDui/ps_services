package com.kapitonau.ps.aggregatorservice.repository.custom

import com.kapitonau.ps.apirequestlib.aggregate.dto.task.TaskStatusResponse
import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse

interface CustomTaskRepository {

    fun countTasksByFilters(workspaceId: Long, projects: List<Long>?): Long

    fun findAllTasksByFilters(workspaceId: Long, projects: List<Long>?, offset: Long, limit: Long): List<TaskResponse>
    fun findAllTasksByFiltersGroupByStatus(workspaceId: Long, projects: List<Long>?): List<TaskStatusResponse>

}