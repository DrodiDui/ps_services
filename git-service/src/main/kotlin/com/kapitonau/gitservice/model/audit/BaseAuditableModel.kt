package com.kapitonau.gitservice.model.audit

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.OffsetDateTime
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseAuditableModel {

    @Column(name = "created_time", nullable = false)
    open var createdTime: ZonedDateTime? = null

    @Column(name = "created_by", nullable = false)
    open var createdBy: Long? = null

    @Column(name = "modified_time", nullable = false)
    open var modifiedTime: ZonedDateTime? = null

    @Column(name = "modified_by", nullable = false)
    open var modifiedBy: Long? = null

}