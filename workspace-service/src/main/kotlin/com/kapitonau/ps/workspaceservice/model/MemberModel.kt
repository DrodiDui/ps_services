package com.kapitonau.ps.workspaceservice.model

import com.kapitonau.ps.workspaceservice.model.audit.BaseAuditModel
import com.kapitonau.ps.workspaceservice.model.audit.BaseAuditModelListener
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "members")
open class MemberModel: BaseAuditModel() {
    @Id
    @Column(name = "member_id", nullable = false)
    open var memberId: Long? = null

    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    open var username: String? = null

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    open var email: String? = null

    @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
    open var firstName: String? = null

    @Column(name = "last_name", nullable = false, length = Integer.MAX_VALUE)
    open var lastName: String? = null

    @Column(name = "middle_name", length = Integer.MAX_VALUE)
    open var middleName: String? = null

    @Column(name = "member_role_id", nullable = false)
    open var memberRoleId: Long? = null

    @Column(name = "member_status_id", nullable = false)
    open var memberStatusId: Long? = null

    @Column(name = "is_deleted", nullable = false)
    open var isDeleted: Boolean? = null
}