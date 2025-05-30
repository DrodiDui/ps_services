package com.kapitonau.taskservice.repository

import com.kapitonau.taskservice.model.TaskCounterModel
import org.springframework.data.jpa.repository.JpaRepository

interface TaskCounterRepository: JpaRepository<TaskCounterModel, Long> {
}