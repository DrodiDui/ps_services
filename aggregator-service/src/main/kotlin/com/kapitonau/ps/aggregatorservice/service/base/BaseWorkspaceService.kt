package com.kapitonau.ps.aggregatorservice.service.base

import com.kapitonau.ps.aggregatorservice.repository.WorkspaceRepository
import com.kapitonau.ps.aggregatorservice.service.WorkspaceService
import com.kapitonau.ps.apirequestlib.aggregate.dto.PageableWorkspaceResponse
import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.commonspringlib.security.ResourceSecurityUtil
import org.springframework.stereotype.Service

@Service
class BaseWorkspaceService (
    private val workspaceRepository: WorkspaceRepository,
) : WorkspaceService {


    override fun getAvailableWorkspaces(
        onlyOwned: Boolean,
        offset: Long,
        limit: Long
    ): PageableWorkspaceResponse {
        val userId = ResourceSecurityUtil.getUserId()
        if (onlyOwned) {

            val workspaceCount = workspaceRepository.countAvailableMemberWorkspace(userId)
            var workspaceList = listOf<WorkspaceResponse>()
            if (workspaceCount > 0) {
                workspaceList = workspaceRepository.getAvailableMemberWorkspace(offset, limit, userId)
            }
            return PageableWorkspaceResponse(workspaceCount, workspaceList)
        } else {

            val workspaceCount = workspaceRepository.countAvailableWorkspaces();
            var workspaceList = listOf<WorkspaceResponse>()
            if (workspaceCount > 0) {
                val availableWorkspaces = workspaceRepository.getAvailableWorkspaces(offset, limit)
                workspaceList = availableWorkspaces;
            }

            return PageableWorkspaceResponse(workspaceCount, workspaceList)
        }
    }

}