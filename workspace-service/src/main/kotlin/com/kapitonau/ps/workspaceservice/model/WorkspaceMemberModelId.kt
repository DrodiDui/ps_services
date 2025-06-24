package com.kapitonau.ps.workspaceservice.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
open class WorkspaceMemberModelId : Serializable {

    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null


    @Column(name = "member_id", nullable = false)
    open var memberId: Long? = null
    override fun hashCode(): Int = Objects.hash(workspaceId, memberId)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as WorkspaceMemberModelId

        return workspaceId == other.workspaceId &&
                memberId == other.memberId
    }

    companion object {
        private const val serialVersionUID = 0L
    }
}