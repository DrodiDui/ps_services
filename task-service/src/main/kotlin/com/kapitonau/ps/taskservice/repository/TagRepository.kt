package com.kapitonau.ps.taskservice.repository

import com.kapitonau.ps.taskservice.model.TagModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TagRepository: JpaRepository<TagModel, Long> {

    fun findAllByTagIdInAndWorkspaceId(ids: List<Long>, workspaceId: Long): List<TagModel>

    fun findByWorkspaceIdAndTagName(workspaceId: Long, tagName: String): Optional<TagModel>

}