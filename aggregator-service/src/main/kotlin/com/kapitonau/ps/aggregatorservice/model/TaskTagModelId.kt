package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class TaskTagModelId : Serializable {

    @Column(name = "task_id", nullable = false)
    open var taskId: Long? = null

    @Column(name = "tag_id", nullable = false)
    open var tagId: Long? = null


    override fun hashCode(): Int = Objects.hash(taskId, tagId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as TaskTagModelId

        return taskId == other.taskId &&
                tagId == other.tagId
    }

    companion object {
        private const val serialVersionUID = 0L
    }
}