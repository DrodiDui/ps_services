package com.kapitonau.taskservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "task_counter")
open class TaskCounterModel {
    @Id
    @Column(name = "project_id", nullable = false)
    open var id: Long? = null

    @Column(name = "task_counter", nullable = false)
    open var taskCounter: Long? = null
}