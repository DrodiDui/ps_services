package com.kapitonau.ps.workspaceservice.repository

import com.kapitonau.ps.workspaceservice.model.ProjectModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ProjectRepository : JpaRepository<ProjectModel, Long> {

    fun findByProjectTitle(title: String): Optional<ProjectModel>

}
