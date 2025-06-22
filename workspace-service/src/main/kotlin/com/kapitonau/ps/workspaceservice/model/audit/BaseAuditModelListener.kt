package com.kapitonau.ps.workspaceservice.model.audit

import com.kapitonau.ps.commonspringlib.security.ResourceSecurityUtil
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime


class BaseAuditModelListener {

    @PrePersist
    fun prePersist(auditModel: BaseAuditModel) {
        auditModel.createdDate = ZonedDateTime.now()
        auditModel.createdBy = ResourceSecurityUtil.getUserId()
        auditModel.version = ResourceSecurityUtil.getUserId()
    }

    @PreUpdate
    fun preUpdate(auditModel: BaseAuditModel) {
        auditModel.lastModifiedDate = ZonedDateTime.now();
        auditModel.lastModifiedBy = ResourceSecurityUtil.getUserId()
        auditModel.version = auditModel.version?.plus(1);
    }

}
