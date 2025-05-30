package com.kapitonau.taskservice.model.audit

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseAuditableModel {

    @CreatedDate
    @Column(name = "created_time")
    open var createdTime: ZonedDateTime? = null

    @CreatedBy
    @Column(name = "created_by")
    open var createdBy: Long? = null

    @LastModifiedDate
    @Column(name = "modified_time")
    open var modifiedTime: ZonedDateTime? = null

    @LastModifiedDate
    @Column(name = "modified_by")
    open var modifiedBy: Long? = null

}