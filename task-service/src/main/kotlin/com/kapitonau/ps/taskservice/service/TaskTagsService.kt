package com.kapitonau.ps.taskservice.service

import org.springframework.stereotype.Service

interface TaskTagsService {

    fun addTagsToTask(taskId: Long, tags: List<Long>, workspaceId: Long)

}