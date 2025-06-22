package com.kapitonau.ps.aggregatorservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "members")
open class MemberModel {
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