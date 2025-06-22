package com.kapitonau.ps.taskservice.controller

import com.kapitonau.ps.apirequestlib.common.tasks.TagResponse
import com.kapitonau.ps.apirequestlib.tasks.api.TaskTagApi
import com.kapitonau.ps.apirequestlib.tasks.dto.TagBatchPostRequest
import com.kapitonau.ps.apirequestlib.tasks.dto.TagPostRequest
import com.kapitonau.ps.taskservice.service.TagService
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskTagController(
    private val tagService: TagService
) : TaskTagApi {
    override fun createTag(body: TagPostRequest): TagResponse =
        tagService.createTag(body)

    override fun createTagBatch(body: TagBatchPostRequest): List<TagResponse> =
        tagService.createTagBatch(body)
}