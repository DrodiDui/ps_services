package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.TaskTagModel
import com.kapitonau.ps.aggregatorservice.model.TaskTagModelId
import org.springframework.data.jpa.repository.JpaRepository

interface TaskTagRepository: JpaRepository<TaskTagModel, TaskTagModelId> {
}