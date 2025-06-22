package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "task_tags")
open class TaskTagModel {
    @EmbeddedId
    open var taskTagId: TaskTagModelId? = null

}