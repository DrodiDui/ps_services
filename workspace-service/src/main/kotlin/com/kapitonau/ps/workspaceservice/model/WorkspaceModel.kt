package com.kapitonau.ps.workspaceservice.model

import com.kapitonau.ps.workspaceservice.model.audit.BaseAuditModel
import com.kapitonau.ps.workspaceservice.model.audit.BaseAuditModelListener
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "workspaces")
@EntityListeners(BaseAuditModelListener::class)
open class WorkspaceModel: BaseAuditModel() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workspaces_id_gen")
    @SequenceGenerator(name = "workspaces_id_gen", sequenceName = "workspaces_workspace_id_seq", allocationSize = 1)
    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null

    @Column(name = "workspace_name", nullable = false, length = 256)
    open var workspaceName: String? = null

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "owner_id", nullable = false)
    open var ownerId: Long? = null

    @Column(name = "is_deleted", nullable = false)
    open var isDeleted: Boolean? = null
}