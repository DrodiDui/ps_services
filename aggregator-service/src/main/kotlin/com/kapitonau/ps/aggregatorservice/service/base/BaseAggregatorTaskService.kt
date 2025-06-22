package com.kapitonau.ps.aggregatorservice.service.base

import com.kapitonau.ps.aggregatorservice.repository.custom.CustomTaskRepository
import com.kapitonau.ps.aggregatorservice.service.AggregatorTaskService
import com.kapitonau.ps.apirequestlib.aggregate.dto.task.PageableTaskResponse
import com.kapitonau.ps.apirequestlib.aggregate.dto.task.TaskStatusResponse
import org.springframework.stereotype.Service

@Service
class BaseAggregatorTaskService(
    private val customTaskRepository: CustomTaskRepository
) : AggregatorTaskService {

    override fun getAllTasksByFilters(workspaceId: Long, projects: List<Long>?, offset: Long, limit: Long): PageableTaskResponse {

        val count = customTaskRepository.countTasksByFilters(workspaceId, projects)

        if (count == 0L) {
            return PageableTaskResponse(count, listOf())
        }

        return PageableTaskResponse(
            count,
            customTaskRepository.findAllTasksByFilters(workspaceId, projects, offset, limit)
        )
    }

    override fun getAllTaskByFilterGroupByStatus(
        workspaceId: Long,
        projects: List<Long>?
    ): List<TaskStatusResponse> {

        return customTaskRepository.findAllTasksByFilters(workspaceId, projects, 0, 100)
            .groupBy { it.taskStatus }
            .map { entry -> TaskStatusResponse(entry.key, entry.value) }
    }
}