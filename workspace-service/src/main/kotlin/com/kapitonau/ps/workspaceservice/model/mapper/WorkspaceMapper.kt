package com.kapitonau.ps.workspaceservice.model.mapper

import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.apirequestlib.workspaces.dto.WorkspacePostRequest
import com.kapitonau.ps.commonspringlib.security.ResourceSecurityUtil
import com.kapitonau.ps.workspaceservice.model.WorkspaceModel
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class WorkspaceMapper {

    fun toModel(requestBody: WorkspacePostRequest): WorkspaceModel {
        val workspaceModel = WorkspaceModel()
        workspaceModel.workspaceName = requestBody.workspaceName
        workspaceModel.description = requestBody.description
        workspaceModel.ownerId = ResourceSecurityUtil.getUserId()
        workspaceModel.isDeleted = true

        return workspaceModel
    }

    fun toModel(requestBody: WorkspacePostRequest, ownerId: Long): WorkspaceModel {
        val workspaceModel = WorkspaceModel()
        workspaceModel.workspaceName = requestBody.workspaceName
        workspaceModel.description = requestBody.description
        workspaceModel.ownerId = ownerId
        workspaceModel.isDeleted = true
        workspaceModel.createdBy = ownerId
        workspaceModel.createdDate = ZonedDateTime.now()
        workspaceModel.lastModifiedDate = ZonedDateTime.now()
        workspaceModel.lastModifiedBy = ownerId

        return workspaceModel
    }

    fun toDto(model: WorkspaceModel): WorkspaceResponse {
        return WorkspaceResponse(
            model.workspaceId,
            model.workspaceName,
            model.description,
            model.createdDate,
            null
        )
    }

}