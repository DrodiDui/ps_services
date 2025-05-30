package com.kapitonau.gitservice.model.mapper

import com.kapitonau.gitservice.cache.CacheProvider
import com.kapitonau.gitservice.model.BranchModel
import com.kapitonau.projectstudio.gitservice.dto.branch.BranchResponse
import org.springframework.stereotype.Component

@Component
class BranchMapper(
    private val cacheProvider: CacheProvider
) {

    fun map(model: BranchModel): BranchResponse {
        val response = BranchResponse()

        response.branchId = model.branchId
        response.branchName = model.branchName
        response.branchVersion = model.branchVersion
        response.createdTime = model.createdTime
        response.modifiedTime = model.modifiedTime
        model.modifiedBy?.let { response.lastModifiedBy = cacheProvider.getEmployee(it) }

        return response;
    }

}
