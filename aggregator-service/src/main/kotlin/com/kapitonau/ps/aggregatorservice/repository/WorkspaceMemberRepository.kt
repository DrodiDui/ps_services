package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.WorkspaceMemberModel
import com.kapitonau.ps.aggregatorservice.model.WorkspaceMemberModelId
import org.springframework.data.jpa.repository.JpaRepository

interface WorkspaceMemberRepository: JpaRepository<WorkspaceMemberModel, WorkspaceMemberModelId> {
}