package com.kapitonau.ps.workspaceservice.model.audit

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
abstract class BaseAuditModel {

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    open var createdDate: ZonedDateTime? = null

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    open var createdBy: Long? = null

    @LastModifiedDate
    @Column(name = "last_modified_date")
    open var lastModifiedDate: ZonedDateTime? = null

    @LastModifiedBy
    @Column(name = "last_modified_by")
    open var lastModifiedBy: Long? = null

    @Version
    @Column(name = "version", nullable = false)
    open var version: Long? = null

}