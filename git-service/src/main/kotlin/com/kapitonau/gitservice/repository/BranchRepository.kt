package com.kapitonau.gitservice.repository

import com.kapitonau.gitservice.model.BranchModel
import com.kapitonau.projectstudio.gitservice.dto.branch.BranchResponse
import org.springframework.data.jpa.repository.JpaRepository

interface BranchRepository: JpaRepository<BranchModel, Long> {

    fun findAllByRepositoryId(repositoryId: Long): List<BranchModel>

}
