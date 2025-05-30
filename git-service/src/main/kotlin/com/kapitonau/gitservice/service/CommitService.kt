package com.kapitonau.gitservice.service

import com.kapitonau.projectstudio.gitservice.dto.commit.CommitResponse
import com.kapitonau.projectstudio.gitservice.dto.commit.DatedCommitResponse

interface CommitService {
    fun getCommitById(commitId: Long): CommitResponse
    fun getBranchCommits(branchId: Long): List<DatedCommitResponse>
    fun getLastBranchCommit(branchId: Long): CommitResponse

}
