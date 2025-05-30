package com.kapitonau.gitservice.service.base

import com.kapitonau.commonspring.exception.CommonServiceException
import com.kapitonau.gitservice.model.mapper.GitRepositoryMapper
import com.kapitonau.gitservice.repository.GitRepositoryRepository
import com.kapitonau.gitservice.service.GitRepositoryService
import com.kapitonau.projectstudio.gitservice.dto.RepositoryResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder.getLocale
import org.springframework.stereotype.Service

@Service
class BaseGitRepositoryService(
    private val gitRepositoryMapper: GitRepositoryMapper,
    private val gitRepository: GitRepositoryRepository,
    private val messageSource: MessageSource
) : GitRepositoryService {

    override fun getProjectRepository(projectId: Long): RepositoryResponse {
        TODO("Not yet implemented")
    }

    override fun getRepository(repositoryId: Long): RepositoryResponse {
        return gitRepository.findById(repositoryId)
            .map { gitRepositoryMapper.map(it) }
            .orElseThrow { CommonServiceException("GIT_SERVICE", messageSource.getMessage("GIT_SERVICE_1", null, getLocale())) }
    }

    override fun getAllRepositories(): List<RepositoryResponse> {
        return gitRepository.findAll()
            .map { gitRepositoryMapper.map(it) }
            .toList()
    }
}