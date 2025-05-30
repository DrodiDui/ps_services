package com.kapitonau.gitservice.model

import com.kapitonau.gitservice.model.audit.BaseAuditableListener
import com.kapitonau.gitservice.model.audit.BaseAuditableModel
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.OffsetDateTime

@Entity
@Table(name = "branches")
@EntityListeners(BaseAuditableListener::class)
open class BranchModel: BaseAuditableModel() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branches_id_gen")
    @SequenceGenerator(name = "branches_id_gen", sequenceName = "branches_branch_id_seq", allocationSize = 1)
    @Column(name = "branch_id", nullable = false)
    open var branchId: Long? = null

    @Column(name = "branch_name", nullable = false, length = Integer.MAX_VALUE)
    open var branchName: String? = null

    @Column(name = "repository_id", nullable = false)
    open var repositoryId: Long? = null

    @ColumnDefault("1")
    @Column(name = "branch_version", nullable = false)
    open var branchVersion: Long? = null
}