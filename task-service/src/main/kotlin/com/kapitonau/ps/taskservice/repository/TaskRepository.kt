package com.kapitonau.ps.taskservice.repository

import com.kapitonau.ps.taskservice.model.TaskModel
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository: JpaRepository<TaskModel, Long> {
}