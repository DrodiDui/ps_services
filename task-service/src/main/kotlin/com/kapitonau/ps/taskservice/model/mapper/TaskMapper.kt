package com.kapitonau.ps.taskservice.model.mapper

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.tasks.TagResponse
import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import com.kapitonau.ps.apirequestlib.tasks.dto.TaskPostRequest
import com.kapitonau.ps.commonspringlib.security.ResourceSecurityUtil
import com.kapitonau.ps.taskservice.cache.MemberCache
import com.kapitonau.ps.taskservice.cache.ProjectCache
import com.kapitonau.ps.taskservice.cache.WorkspaceCache
import com.kapitonau.ps.taskservice.model.TaskModel
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class TaskMapper(
    private val referenceCache: ReferenceCache,
    private val memberCache: MemberCache,
    private val workspaceCache: WorkspaceCache,
    private val projectCache: ProjectCache,
) {

    fun toModel(
        body: TaskPostRequest,
    ): TaskModel {
        val currentUser = ResourceSecurityUtil.getUserId()

        val model = TaskModel()
        model.taskTitle = body.title
        model.description = body.description
        model.ownerId = currentUser
        if (body.assigneeId == null) {
            model.assigneeId = currentUser
        } else {
            model.assigneeId = body.assigneeId
        }
        model.taskStatusId =
            referenceCache.getReferenceItemByTypeAndCode("TASK_STATUS", body.taskStatusCode).referenceItemId
        model.taskPriorityId =
            referenceCache.getReferenceItemByTypeAndCode("TASK_PRIORITY", body.taskPriorityCode).referenceItemId
        model.createdDate = ZonedDateTime.now()
        model.createdBy = currentUser
        model.lastModifiedDate = ZonedDateTime.now()
        model.lastModifiedBy = currentUser
        model.projectId = body.projectId
        model.taskTypeId = referenceCache.getReferenceItemByTypeAndCode("TASK_TYPE", body.taskTypeCode).referenceItemId
        model.workspaceId = body.workspaceId
        return model
    }

    fun toResponse(model: TaskModel): TaskResponse {
        return TaskResponse(
            model.taskId,
            model.taskName,
            model.taskTitle,
            model.description,
            referenceCache.getReferenceItemById(model.taskStatusId),
            referenceCache.getReferenceItemById(model.taskPriorityId),
            referenceCache.getReferenceItemById(model.taskTypeId),
            projectCache.getProjectById(model.projectId!!),
            workspaceCache.getWorkspaceById(model.workspaceId!!),
            memberCache.getMemberById(model.ownerId!!),
            memberCache.getMemberById(model.assigneeId!!),
            model.createdDate,
            model.lastModifiedDate,
            listOf<TagResponse>()
        )
    }

}