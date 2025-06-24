package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.ProjectModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ProjectRepository: JpaRepository<ProjectModel, Long> {

    fun findAllByWorkspaceIdAndIsDeleted(workspaceId: Long, isDeleted: Boolean): List<ProjectModel>
    fun findByWorkspaceIdAndProjectId(workspaceId: Long, projectId: Long): Optional<ProjectModel>

}
