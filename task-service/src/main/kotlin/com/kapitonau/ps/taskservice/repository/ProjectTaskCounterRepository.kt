package com.kapitonau.ps.taskservice.repository

import com.kapitonau.ps.taskservice.model.ProjectTaskCounterModel
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectTaskCounterRepository: JpaRepository<ProjectTaskCounterModel, Long> {
}