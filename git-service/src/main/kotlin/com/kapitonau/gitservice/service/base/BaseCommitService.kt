package com.kapitonau.gitservice.service.base

import com.kapitonau.commonspring.exception.CommonServiceException
import com.kapitonau.gitservice.model.CommitModel
import com.kapitonau.gitservice.model.mapper.CommitMapper
import com.kapitonau.gitservice.repository.CommitRepository
import com.kapitonau.gitservice.service.CommitService
import com.kapitonau.projectstudio.gitservice.dto.commit.CommitResponse
import com.kapitonau.projectstudio.gitservice.dto.commit.DatedCommitResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder.getLocale
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.function.Function
import java.util.stream.Collectors
import kotlin.jvm.optionals.toList
import kotlin.streams.toList

@Service
class BaseCommitService(
    private val messageSource: MessageSource,
    private val commitMapper: CommitMapper,
    private val commitRepository: CommitRepository
) : CommitService {

    override fun getCommitById(commitId: Long): CommitResponse {
        return commitRepository.findById(commitId)
            .map { commitMapper.map(it) }
            .orElseThrow { CommonServiceException("GIT_SERVICE", messageSource.getMessage("GIT_SERVICE_2", null, getLocale())) }
    }

    override fun getBranchCommits(branchId: Long): List<DatedCommitResponse> {

        val groupedCommits = mutableMapOf<ZonedDateTime, MutableList<CommitResponse>>()
        for (projection in commitRepository.findAllByBranchId(branchId)) {
            if (groupedCommits.containsKey(projection.getCommitTime())) {
                groupedCommits[projection.getCommitTime()]?.add(commitMapper.map(projection.getCommit()))
            } else {
                groupedCommits.put(projection.getCommitTime(), mutableListOf(commitMapper.map(projection.getCommit())))
            }
        }

        return groupedCommits.entries.stream()
            .map { entry -> DatedCommitResponse(entry.key, entry.value) }
            .toList()
    }

    override fun getLastBranchCommit(branchId: Long): CommitResponse {
        return commitRepository.getLastCommitByBranchId(branchId)
            .map { commitMapper.map(it) }
            .orElseThrow { CommonServiceException("GIT_SERVICE", messageSource.getMessage("GIT_SERVICE_2", null, getLocale())) }

    }
}