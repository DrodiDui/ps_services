package com.kapitonau.gitservice.controller

import com.kapitonau.gitservice.service.CommitService
import com.kapitonau.projectstudio.gitservice.api.CommitApi
import com.kapitonau.projectstudio.gitservice.dto.commit.CommitResponse
import com.kapitonau.projectstudio.gitservice.dto.commit.DatedCommitResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class CommitController(
    private val commitService: CommitService
): CommitApi {
    override fun getCommitById(commitId: Long): CommitResponse =
        commitService.getCommitById(commitId)

    override fun getBranchCommits(branchId: Long): List<DatedCommitResponse> =
        commitService.getBranchCommits(branchId)

    override fun getLastBranchCommits(branchId: Long): CommitResponse =
        commitService.getLastBranchCommit(branchId)
}