package com.kapitonau.gitservice.model

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.time.ZonedDateTime

@Entity
@Table(name = "commits")
open class CommitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commits_id_gen")
    @SequenceGenerator(name = "commits_id_gen", sequenceName = "commits_commit_id_seq", allocationSize = 1)
    @Column(name = "commit_id", nullable = false)
    open var commitId: Long? = null

    @Column(name = "branch_id", nullable = false)
    open var branchId: Long? = null

    @Column(name = "commit", nullable = false, length = Integer.MAX_VALUE)
    open var commit: String? = null

    @Column(name = "message", nullable = false)
    open var message: String? = null

    @Column(name = "commit_time", nullable = false)
    open var commitTime: ZonedDateTime? = null

    @Column(name = "commit_author", nullable = false)
    open var commitAuthor: Long? = null
}