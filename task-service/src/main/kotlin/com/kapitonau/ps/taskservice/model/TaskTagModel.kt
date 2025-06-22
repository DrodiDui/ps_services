package com.kapitonau.ps.taskservice.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "task_tags")
open class TaskTagModel {
    @EmbeddedId
    @SequenceGenerator(name = "task_tags_id_gen", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    open var taskTagId: TaskTagModelId? = null

}