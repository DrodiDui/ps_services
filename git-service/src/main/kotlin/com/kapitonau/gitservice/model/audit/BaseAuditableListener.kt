package com.kapitonau.gitservice.model.audit

import com.kapitonau.commonspring.utils.ResourceServerUtil
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime
import kotlin.math.min

class BaseAuditableListener {
    @PrePersist
    fun prePersist(model: BaseAuditableModel) {
        if (model.createdBy == null) {
            model.createdBy = ResourceServerUtil.getUserId()
        }
        model.createdTime = ZonedDateTime.now()
        if (model.modifiedBy == null) {
            model.modifiedBy = ResourceServerUtil.getUserId()
        }
        model.modifiedTime = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate(model: BaseAuditableModel) {
        if (model.modifiedBy == null) {
            model.modifiedBy = ResourceServerUtil.getUserId()
        }
        model.modifiedTime = ZonedDateTime.now()
    }
}