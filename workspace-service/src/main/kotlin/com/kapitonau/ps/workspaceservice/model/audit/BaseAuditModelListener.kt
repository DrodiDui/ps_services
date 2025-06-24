package com.kapitonau.ps.workspaceservice.model.audit

import com.kapitonau.ps.commonspringlib.security.ResourceSecurityUtil
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime


class BaseAuditModelListener {

    @PrePersist
    fun prePersist(auditModel: BaseAuditModel) {
        if (auditModel.createdDate == null) auditModel.createdDate = ZonedDateTime.now()
        if (auditModel.createdBy == null) auditModel.createdBy =  ResourceSecurityUtil.getUserId()
        if (auditModel.version == null) auditModel.version = 1
    }

    @PreUpdate
    fun preUpdate(auditModel: BaseAuditModel) {
        auditModel.lastModifiedDate = ZonedDateTime.now();
        auditModel.lastModifiedBy = ResourceSecurityUtil.getUserId()
        auditModel.version = auditModel.version?.plus(1);
    }

}
