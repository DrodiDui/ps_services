package com.kapitonau.taskservice.model

import com.kapitonau.taskservice.model.audit.BaseAuditableModel
import com.kapitonau.taskservice.model.audit.BaseAuditableModelListener
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tasks")
@EntityListeners(BaseAuditableModelListener::class)
open class TaskModel : BaseAuditableModel() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_gen")
    @SequenceGenerator(name = "tasks_id_gen", sequenceName = "tasks_task_id_seq", allocationSize = 1)
    @Column(name = "task_id", nullable = false)
    open var id: Long? = null

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    open var name: String? = null

    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    open var title: String? = null

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "project_id", nullable = false)
    open var projectId: Long? = null

    @Column(name = "task_status_id", nullable = false)
    open var taskStatusId: Long? = null

    @Column(name = "task_priority_id", nullable = false)
    open var taskPriorityId: Long? = null

    @Column(name = "start_date", nullable = false)
    open var startDate: LocalDate? = null

    @Column(name = "due_date")
    open var dueDate: LocalDate? = null

    @Column(name = "owner_id", nullable = false)
    open var ownerId: Long? = null

    @Column(name = "assignee_id", nullable = false)
    open var assigneeId: Long? = null

    @Column(name = "task_type_id", nullable = true)
    open var taskTypeId: Long? = null
}