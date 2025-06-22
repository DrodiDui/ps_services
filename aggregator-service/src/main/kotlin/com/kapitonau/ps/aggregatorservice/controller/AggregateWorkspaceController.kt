package com.kapitonau.ps.aggregatorservice.controller

import com.kapitonau.ps.aggregatorservice.service.WorkspaceService
import com.kapitonau.ps.apirequestlib.aggregate.api.AggregateWorkspaceApi
import com.kapitonau.ps.apirequestlib.aggregate.dto.PageableWorkspaceResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class AggregateWorkspaceController (
    val workspaceService: WorkspaceService,
): AggregateWorkspaceApi {
    override fun getAllAvailableWorkspaces(
        offset: Long,
        limit: Long,
        onlyOwned: Boolean
    ): PageableWorkspaceResponse? =
        workspaceService.getAvailableWorkspaces(onlyOwned, offset, limit)

}
