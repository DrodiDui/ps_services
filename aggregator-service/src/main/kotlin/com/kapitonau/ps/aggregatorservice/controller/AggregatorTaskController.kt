package com.kapitonau.ps.aggregatorservice.controller

import com.kapitonau.ps.aggregatorservice.service.AggregatorTaskService
import com.kapitonau.ps.apirequestlib.aggregate.api.AggregatorTaskApi
import com.kapitonau.ps.apirequestlib.aggregate.dto.task.PageableTaskResponse
import com.kapitonau.ps.apirequestlib.aggregate.dto.task.TaskStatusResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class AggregatorTaskController(
    private val aggregatorTaskService: AggregatorTaskService
): AggregatorTaskApi {
    override fun getTasks(
        workspaceId: Long,
        projects: List<Long>?,
        offset: Long,
        limit: Long
    ): PageableTaskResponse? {
        return aggregatorTaskService.getAllTasksByFilters(workspaceId, projects, offset, limit)
    }

    override fun getAllTasksByStatus(
        workspaceId: Long,
        projects: List<Long>?
    ): List<TaskStatusResponse> =
        aggregatorTaskService.getAllTaskByFilterGroupByStatus(workspaceId, projects)

}