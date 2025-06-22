package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "workspaces")
open class WorkspaceModel {

    @Id
    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null

    @Column(name = "workspace_name", nullable = false, length = 256)
    open var workspaceName: String? = null

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "owner_id", nullable = false)
    open var ownerId: Long? = null

    @Column(name = "created_date", nullable = false)
    open var createdDate: ZonedDateTime? = null

    @Column(name = "created_by", nullable = false)
    open var createdBy: Long? = null

    @Column(name = "last_modified_date")
    open var lastModifiedDate: ZonedDateTime? = null

    @Column(name = "last_modified_by")
    open var lastModifiedBy: Long? = null

    @Column(name = "version", nullable = false)
    open var version: Long? = null

    @Column(name = "is_deleted", nullable = false)
    open var isDeleted: Boolean? = null
}