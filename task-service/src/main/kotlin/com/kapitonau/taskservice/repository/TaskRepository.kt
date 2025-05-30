package com.kapitonau.taskservice.repository

import com.kapitonau.taskservice.model.TaskModel
import com.kapitonau.taskservice.repository.custom.CustomTaskRepository
import com.kapitonau.taskservice.repository.projection.StatusTaskProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskRepository : JpaRepository<TaskModel, Long>, CustomTaskRepository {

    @Query("""
        select t.taskStatusId as statusId, t as taskModel from TaskModel t 
        order by t.taskPriorityId desc, t.dueDate desc 
    """)
    fun findAllAndGroupByStatus(): List<StatusTaskProjection>

}
