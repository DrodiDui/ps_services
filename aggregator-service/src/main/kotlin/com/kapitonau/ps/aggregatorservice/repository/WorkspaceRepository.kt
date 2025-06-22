package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.WorkspaceModel
import com.kapitonau.ps.aggregatorservice.repository.custom.CustomWorkspaceRepository
import org.springframework.data.jpa.repository.JpaRepository

interface WorkspaceRepository: JpaRepository<WorkspaceModel, Long>, CustomWorkspaceRepository {
}