package com.kapitonau.ps.taskservice.service

import com.kapitonau.ps.apirequestlib.common.tasks.TagResponse
import com.kapitonau.ps.apirequestlib.tasks.dto.TagBatchPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TagPostRequest

interface TagService {
    fun createTag(body: TagPostRequest): TagResponse
    fun createTagBatch(body: TagBatchPostRequest): List<TagResponse>

}
