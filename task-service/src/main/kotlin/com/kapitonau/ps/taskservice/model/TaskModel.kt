package com.kapitonau.ps.taskservice.model

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "tasks")
open class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_gen")
    @SequenceGenerator(name = "tasks_id_gen", sequenceName = "tasks_task_id_seq", allocationSize = 1)
    @Column(name = "task_id", nullable = false)
    open var taskId: Long? = null

    @Column(name = "task_name", nullable = false, length = Integer.MAX_VALUE)
    open var taskName: String? = null

    @Column(name = "task_title", nullable = false, length = Integer.MAX_VALUE)
    open var taskTitle: String? = null

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "owner_id", nullable = false)
    open var ownerId: Long? = null

    @Column(name = "assignee_id", nullable = false)
    open var assigneeId: Long? = null

    @Column(name = "task_status_id", nullable = false)
    open var taskStatusId: Long? = null

    @Column(name = "task_priority_id", nullable = false)
    open var taskPriorityId: Long? = null

    @Column(name = "task_type_id", nullable = false)
    open var taskTypeId: Long? = null

    @Column(name = "project_id", nullable = false)
    open var projectId: Long? = null

    @Column(name = "workspace_id", nullable = false)
    open var workspaceId: Long? = null

    @Column(name = "created_date", nullable = false)
    open var createdDate: ZonedDateTime? = null

    @Column(name = "created_by", nullable = false)
    open var createdBy: Long? = null

    @Column(name = "last_modified_date", nullable = false)
    open var lastModifiedDate: ZonedDateTime? = null

    @Column(name = "last_modified_by", nullable = false)
    open var lastModifiedBy: Long? = null
}