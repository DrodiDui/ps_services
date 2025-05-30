package com.kapitonau.gitservice.service.base

import com.kapitonau.commonspring.exception.CommonServiceException
import com.kapitonau.gitservice.provider.github.client.GitHubContentClient
import com.kapitonau.gitservice.repository.GitRepositoryRepository
import com.kapitonau.gitservice.repository.SettingRepository
import com.kapitonau.gitservice.service.RepositoryContentService
import com.kapitonau.projectstudio.gitservice.dto.content.GitContentGetRequest
import com.kapitonau.projectstudio.gitservice.dto.content.GitDirectoryResponse
import com.kapitonau.projectstudio.gitservice.dto.content.GitFileResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder.getLocale
import org.springframework.stereotype.Service

@Service
class BaseRepositoryContentService(
    private val messageSource: MessageSource,
    private val settingRepository: SettingRepository,
    private val gitRepository: GitRepositoryRepository,
    private val gitHubContentClient: GitHubContentClient,
) : RepositoryContentService {


    override fun getDirectories(body: GitContentGetRequest): List<GitDirectoryResponse> {

        val gitModel = gitRepository.findByProjectId(body.projectId)
            .orElseThrow {
                CommonServiceException(
                    "GIT_SERVICE",
                    messageSource.getMessage("GIT_SERVICE_1", null, getLocale())
                )
            }

        val settings = settingRepository.findByRepository_RepositoryId(gitModel.repositoryId!!)
            .orElseThrow {
                CommonServiceException(
                    "GIT_SERVICE",
                    messageSource.getMessage("GIT_SERVICE_3", null, getLocale())
                )
            }


        val repositoryContents = gitHubContentClient.getRepositoryContents(
            settings.settings?.get("repositoryOwner").toString(),
            settings.settings?.get("repositoryName").toString(),
            body.path,
            settings.settings?.get("accessToken").toString()
        )

        return repositoryContents.stream()
            .map { it -> GitDirectoryResponse(it.name, it.path, it.sha, it.size.toLong(), it.type) }
            .toList()
            .sortedBy { it.type }

    }

    override fun getFile(body: GitContentGetRequest): GitFileResponse {
        val gitModel = gitRepository.findByProjectId(body.projectId)
            .orElseThrow {
                CommonServiceException(
                    "GIT_SERVICE",
                    messageSource.getMessage("GIT_SERVICE_1", null, getLocale())
                )
            }

        val settings = settingRepository.findByRepository_RepositoryId(gitModel.repositoryId!!)
            .orElseThrow {
                CommonServiceException(
                    "GIT_SERVICE",
                    messageSource.getMessage("GIT_SERVICE_3", null, getLocale())
                )
            }


        val repositoryContents = gitHubContentClient.getRepositoryContent(
            settings.settings?.get("repositoryOwner").toString(),
            settings.settings?.get("repositoryName").toString(),
            body.path,
            settings.settings?.get("accessToken").toString()
        )

        val content = repositoryContents.content?.replace("\n", "")

        return GitFileResponse(
            repositoryContents.name,
            repositoryContents.path,
            repositoryContents.sha,
            repositoryContents.size.toLong(),
            repositoryContents.type,
            content,
            repositoryContents.encoding
        )
    }

}