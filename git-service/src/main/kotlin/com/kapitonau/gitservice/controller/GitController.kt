package com.kapitonau.gitservice.controller

import com.kapitonau.gitservice.service.GitRepositoryService
import com.kapitonau.projectstudio.gitservice.api.GitApi
import com.kapitonau.projectstudio.gitservice.dto.RepositoryResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class GitController(
    private val gitRepositoryService: GitRepositoryService
) : GitApi {


    override fun getRepositoryByProjectId(projectId: Long): RepositoryResponse =
        gitRepositoryService.getProjectRepository(projectId)

    override fun getRepository(repositoryId: Long): RepositoryResponse =
        gitRepositoryService.getRepository(repositoryId)

    override fun getRepositories(): List<RepositoryResponse> =
        gitRepositoryService.getAllRepositories()


}