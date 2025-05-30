package com.kapitonau.gitservice.service

import com.kapitonau.projectstudio.gitservice.dto.RepositoryResponse

interface GitRepositoryService {
    fun getProjectRepository(projectId: Long): RepositoryResponse
    fun getRepository(repositoryId: Long): RepositoryResponse
    fun getAllRepositories(): List<RepositoryResponse>

}
