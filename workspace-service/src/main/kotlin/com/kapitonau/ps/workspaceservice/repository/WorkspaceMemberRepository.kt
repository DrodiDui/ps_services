package com.kapitonau.ps.workspaceservice.repository

import com.kapitonau.ps.workspaceservice.model.WorkspaceMemberModel
import com.kapitonau.ps.workspaceservice.model.WorkspaceMemberModelId
import org.springframework.data.jpa.repository.JpaRepository

interface WorkspaceMemberRepository: JpaRepository<WorkspaceMemberModel, WorkspaceMemberModelId> {
}