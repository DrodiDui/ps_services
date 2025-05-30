package com.kapitonau.gitservice.service.base

import com.kapitonau.gitservice.model.mapper.BranchMapper
import com.kapitonau.gitservice.repository.BranchRepository
import com.kapitonau.gitservice.service.BranchService
import com.kapitonau.projectstudio.gitservice.dto.branch.BranchResponse
import org.springframework.stereotype.Service

@Service
class BaseBranchService(
    private val branchMapper: BranchMapper,
    private val branchRepository: BranchRepository
) : BranchService {
    override fun getRepositoryBranches(repositoryId: Long): List<BranchResponse> {
        return branchRepository.findAllByRepositoryId(repositoryId)
            .map { branchMapper.map(it) }
            .toList()
    }
}