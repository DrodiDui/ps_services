package com.kapitonau.ps.taskservice.model

import jakarta.persistence.*

@Entity
@Table(name = "tags")
open class TagModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_gen")
    @SequenceGenerator(name = "tags_id_gen", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    @Column(name = "tag_id", nullable = false)
    open var tagId: Long? = null

    @Column(name = "tag_name", nullable = false, length = Integer.MAX_VALUE)
    open var tagName: String? = null

    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null
}