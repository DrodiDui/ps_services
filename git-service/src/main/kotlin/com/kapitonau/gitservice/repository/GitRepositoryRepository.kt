package com.kapitonau.gitservice.repository

import com.kapitonau.gitservice.model.GitRepositoryModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface GitRepositoryRepository: JpaRepository<GitRepositoryModel, Long> {

    fun findByProjectId(projectId: Long): Optional<GitRepositoryModel>

}