package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.*

@Entity
@Table(name = "tags")
open class TagModel {
    @Id
    @Column(name = "tag_id", nullable = false)
    open var tagId: Long? = null

    @Column(name = "tag_name", nullable = false, length = Integer.MAX_VALUE)
    open var tagName: String? = null

    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null
}