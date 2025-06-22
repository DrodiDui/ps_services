package com.kapitonau.ps.aggregatorservice.service

import com.kapitonau.ps.apirequestlib.aggregate.dto.task.PageableTaskResponse
import com.kapitonau.ps.apirequestlib.aggregate.dto.task.TaskStatusResponse

interface AggregatorTaskService {
    fun getAllTasksByFilters(workspaceId: Long, projects: List<Long>?, offset: Long, limit: Long): PageableTaskResponse
    fun getAllTaskByFilterGroupByStatus(workspaceId: Long, projects: List<Long>?): List<TaskStatusResponse>

}
