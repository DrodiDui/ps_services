package com.kapitonau.ps.taskservice.repository

import com.kapitonau.ps.taskservice.model.TaskTagModel
import com.kapitonau.ps.taskservice.model.TaskTagModelId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskTagRepository: JpaRepository<TaskTagModel, TaskTagModelId> {

    @Query("delete from TaskTagModel tt where tt.taskTagId.taskId = :taskId")
    fun deleteAllByTaskId(taskId: Long)
}