package com.kapitonau.gitservice.controller

import com.kapitonau.gitservice.service.RepositoryContentService
import com.kapitonau.projectstudio.gitservice.api.ContentApi
import com.kapitonau.projectstudio.gitservice.dto.content.GitContentGetRequest
import com.kapitonau.projectstudio.gitservice.dto.content.GitDirectoryResponse
import com.kapitonau.projectstudio.gitservice.dto.content.GitFileResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class ContentController(
    private val repositoryContentService: RepositoryContentService,
) : ContentApi {
    override fun getDirectories(body: GitContentGetRequest): List<GitDirectoryResponse> =
        repositoryContentService.getDirectories(body)

    override fun getFile(body: GitContentGetRequest): GitFileResponse =
        repositoryContentService.getFile(body)


}
