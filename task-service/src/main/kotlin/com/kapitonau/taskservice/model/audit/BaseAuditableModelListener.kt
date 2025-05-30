package com.kapitonau.taskservice.model.audit

import com.kapitonau.taskservice.model.audit.BaseAuditableModel
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import lombok.extern.slf4j.Slf4j
import java.time.ZonedDateTime

@Slf4j
class BaseAuditableModelListener {
    @PrePersist
    fun prePersist(model: BaseAuditableModel) {
        model.createdBy = 0L
        model.createdTime = ZonedDateTime.now()
        model.modifiedBy = 0L
        model.modifiedTime = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate(model: BaseAuditableModel) {
        model.modifiedBy = 0L
        model.modifiedTime = ZonedDateTime.now()
    }
}
