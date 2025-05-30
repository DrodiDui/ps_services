package com.kapitonau.gitservice.service

import com.kapitonau.projectstudio.gitservice.dto.branch.BranchResponse

interface BranchService {
    fun getRepositoryBranches(repositoryId: Long): List<BranchResponse>

}
