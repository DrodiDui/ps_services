package com.kapitonau.gitservice.controller

import com.kapitonau.gitservice.service.BranchService
import com.kapitonau.projectstudio.gitservice.api.BranchApi
import com.kapitonau.projectstudio.gitservice.dto.branch.BranchResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class BranchController(
    private val branchService: BranchService
) : BranchApi {

    override fun getRepositoryBranches(repositoryId: Long): List<BranchResponse> =
        branchService.getRepositoryBranches(repositoryId);

}