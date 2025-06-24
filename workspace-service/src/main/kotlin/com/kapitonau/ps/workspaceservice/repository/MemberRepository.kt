package com.kapitonau.ps.workspaceservice.repository

import com.kapitonau.ps.workspaceservice.model.MemberModel
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<MemberModel, Long> {
}