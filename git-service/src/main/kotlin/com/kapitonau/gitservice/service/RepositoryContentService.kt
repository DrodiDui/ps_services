package com.kapitonau.gitservice.service

import com.kapitonau.projectstudio.gitservice.dto.content.GitContentGetRequest
import com.kapitonau.projectstudio.gitservice.dto.content.GitDirectoryResponse
import com.kapitonau.projectstudio.gitservice.dto.content.GitFileResponse

interface RepositoryContentService {
    fun getDirectories(body: GitContentGetRequest): List<GitDirectoryResponse>
    fun getFile(body: GitContentGetRequest): GitFileResponse


}