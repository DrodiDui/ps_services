package com.kapitonau.ps.workspaceservice.repository

import com.kapitonau.ps.workspaceservice.model.WorkspaceModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface WorkspaceRepository: JpaRepository<WorkspaceModel, Long> {
    fun findByWorkspaceName(workspaceName: String): Optional<WorkspaceModel>
}