package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "workspace_members")
open class WorkspaceMemberModel {
    @EmbeddedId
    open var workspaceMemberId: WorkspaceMemberModelId? = null

    @Column(name = "role_id", nullable = false)
    open var roleId: Long? = null

    @Column(name = "created_date", nullable = false)
    open var createdDate: ZonedDateTime? = null
}