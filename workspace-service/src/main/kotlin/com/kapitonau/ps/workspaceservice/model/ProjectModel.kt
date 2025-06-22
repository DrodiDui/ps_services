package com.kapitonau.ps.workspaceservice.model

import com.kapitonau.ps.workspaceservice.model.audit.BaseAuditModel
import com.kapitonau.ps.workspaceservice.model.audit.BaseAuditModelListener
import jakarta.persistence.*

@Entity
@Table(name = "projects")
@EntityListeners(BaseAuditModelListener::class)
open class ProjectModel: BaseAuditModel() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_id_gen")
    @SequenceGenerator(name = "projects_id_gen", sequenceName = "projects_project_id_seq", allocationSize = 1)
    @Column(name = "project_id", nullable = false)
    open var projectId: Long? = null

    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null

    @Column(name = "project_title", nullable = false, length = 256)
    open var projectTitle: String? = null

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "project_type_id", nullable = false)
    open var projectTypeId: Long? = null

    @Column(name = "is_deleted", nullable = false)
    open var isDeleted: Boolean? = null
}