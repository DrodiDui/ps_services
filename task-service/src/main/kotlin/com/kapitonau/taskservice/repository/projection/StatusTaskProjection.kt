package com.kapitonau.taskservice.repository.projection

import com.kapitonau.taskservice.model.TaskModel

interface StatusTaskProjection {

    fun getStatusId(): Long

    fun getTaskModel(): TaskModel

}