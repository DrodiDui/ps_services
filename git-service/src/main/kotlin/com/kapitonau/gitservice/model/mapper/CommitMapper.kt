package com.kapitonau.gitservice.model.mapper

import com.kapitonau.gitservice.cache.CacheProvider
import com.kapitonau.gitservice.model.CommitModel
import com.kapitonau.projectstudio.gitservice.dto.commit.CommitResponse
import org.springframework.stereotype.Component

@Component
class CommitMapper(
    private val cacheProvider: CacheProvider
) {

    fun map(model: CommitModel): CommitResponse {
        val response = CommitResponse()

        response.commitId = model.commitId
        response.commit = model.commit
        response.message = model.message
        response.commitTime = model.commitTime
        model.commitAuthor?.let { response.commitAuthor = cacheProvider.getEmployee(it) }

        return response
    }

}
