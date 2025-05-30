package com.kapitonau.projectservice.model.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class BaseAuditableModelListener {


    @PrePersist
    public void prePersist(BaseAuditableModel model) {
        model.setCreatedBy(0L);
        model.setCreatedTime(ZonedDateTime.now());
        model.setModifiedBy(0L);
        model.setModifiedTime(ZonedDateTime.now());
    }

    @PreUpdate
    public void preUpdate(BaseAuditableModel model) {
        model.setModifiedBy(0L);
        model.setModifiedTime(ZonedDateTime.now());
    }

}
