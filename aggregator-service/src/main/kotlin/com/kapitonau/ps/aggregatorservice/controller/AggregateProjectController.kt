package com.kapitonau.ps.aggregatorservice.controller

import com.kapitonau.ps.aggregatorservice.service.ProjectService
import com.kapitonau.ps.apirequestlib.aggregate.api.AggregateProjectApi
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class AggregateProjectController(
    private val projectService: ProjectService
) : AggregateProjectApi {

    override fun getAvailableProjects(workspaceId: String): List<ProjectResponse> =
        projectService.getAvailableProjects(workspaceId.toLong())
}