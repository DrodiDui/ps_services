package com.kapitonau.taskservice.model.mapper

import com.kapitonau.commonspring.utils.ResourceServerUtil
import com.kapitonau.projectstudio.bean.cache.ReferenceCache
import com.kapitonau.projectstudio.taskservice.dto.TaskPostRequest
import com.kapitonau.projectstudio.taskservice.dto.TaskResponse
import com.kapitonau.taskservice.cache.EmployeeCache
import com.kapitonau.taskservice.cache.ProjectCache
import com.kapitonau.taskservice.model.TaskModel
import org.springframework.stereotype.Component

@Component
class TaskMapper(
    private val referenceCache: ReferenceCache,
    private val projectCache: ProjectCache,
    private val employeeCache: EmployeeCache
) {


    fun map(model: TaskModel): TaskResponse {
        val response = TaskResponse()

        response.taskId = model.id
        response.name = model.name
        response.title = model.title
        response.description = model.description

        response.taskStatus = referenceCache.getReferenceItemById(model.taskStatusId)
        response.taskPriority = referenceCache.getReferenceItemById(model.taskPriorityId)

        if (model.taskTypeId != null) {
            response.taskType = referenceCache.getReferenceItemById(model.taskTypeId)
        }

        response.startDate = model.startDate
        response.dueDate = model.dueDate

        model.ownerId?.let { response.owner = employeeCache.getEmployee(it) }
        model.assigneeId?.let { response.assignee = employeeCache.getEmployee(it) }

        model.projectId?.let { response.project = projectCache.getProject(it) }

        return response
    }

    fun map(body: TaskPostRequest): TaskModel {
        val model = TaskModel()

        model.title = body.title
        model.description = body.description
        model.projectId = body.projectId
        model.taskStatusId = referenceCache.getReferenceItemByTypeAndCode("TASK_STATUS", body.taskStatus).referenceItemId
        model.taskPriorityId = referenceCache.getReferenceItemByTypeAndCode("TASK_PRIORITY", body.taskPriority).referenceItemId
        body.taskType.let { it -> model.taskTypeId = referenceCache.getReferenceItemByTypeAndCode("TASK_TYPE", it).referenceItemId }
        model.startDate = body.startDate
        model.dueDate = body.dueDate
        model.ownerId = ResourceServerUtil.getUserId()
        if (body.assigneeId != null) {
            model.assigneeId = body.assigneeId
        } else{
            model.assigneeId = ResourceServerUtil.getUserId()
        }

        return model
    }

}