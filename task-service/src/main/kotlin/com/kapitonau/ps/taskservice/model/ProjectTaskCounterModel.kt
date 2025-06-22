package com.kapitonau.ps.taskservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "project_task_counter")
open class ProjectTaskCounterModel {
    @Id
    @Column(name = "project_id", nullable = false)
    open var projectId: Long? = null

    @Column(name = "counter", nullable = false)
    open var counter: Long? = null
}