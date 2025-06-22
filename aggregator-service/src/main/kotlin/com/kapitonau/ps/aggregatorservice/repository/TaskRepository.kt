package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.TaskModel
import com.kapitonau.ps.aggregatorservice.repository.custom.CustomTaskRepository
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository: JpaRepository<TaskModel, Long> {
}