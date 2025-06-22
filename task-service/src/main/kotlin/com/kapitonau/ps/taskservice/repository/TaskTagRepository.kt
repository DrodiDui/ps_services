package com.kapitonau.ps.taskservice.repository

import com.kapitonau.ps.taskservice.model.TaskTagModel
import com.kapitonau.ps.taskservice.model.TaskTagModelId
import org.springframework.data.jpa.repository.JpaRepository

interface TaskTagRepository: JpaRepository<TaskTagModel, TaskTagModelId> {
}