package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "projects")
open class ProjectModel {
    @Id
    @Column(name = "project_id", nullable = false)
    open var projectId: Long? = null

    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null

    @Column(name = "project_title", nullable = false, length = 256)
    open var projectTitle: String? = null

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    open var description: String? = null

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

    @Column(name = "project_type_id", nullable = false)
    open var projectTypeId: Long? = null

    @Column(name = "is_deleted", nullable = false)
    open var isDeleted: Boolean? = null
}