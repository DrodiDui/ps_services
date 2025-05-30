package com.kapitonau.gitservice.model

import com.kapitonau.gitservice.model.audit.BaseAuditableListener
import com.kapitonau.gitservice.model.audit.BaseAuditableModel
import jakarta.persistence.*

@Entity
@Table(name = "repositories")
@EntityListeners(BaseAuditableListener::class)
open class GitRepositoryModel : BaseAuditableModel() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repositories_id_gen")
    @SequenceGenerator(
        name = "repositories_id_gen",
        sequenceName = "repositories_repository_id_seq",
        allocationSize = 1
    )
    @Column(name = "repository_id", nullable = false)
    open var repositoryId: Long? = null

    @Column(name = "repository_name", nullable = false, length = Integer.MAX_VALUE)
    open var repositoryName: String? = null

    @Column(name = "project_id", nullable = false)
    open var projectId: Long? = null

    @Column(name = "owner_id", nullable = false)
    open var ownerId: Long? = null

    @Column(name = "space_id", nullable = false)
    open var spaceId: Long? = null
    
    @Column(name = "git_provider_id", nullable = false)
    open var gitProviderId: Long? = null
}