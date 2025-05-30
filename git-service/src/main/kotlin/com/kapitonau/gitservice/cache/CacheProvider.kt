package com.kapitonau.gitservice.cache

import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse
import com.kapitonau.projectstudio.projectservice.dto.project.ProjectResponse
import org.springframework.stereotype.Component

@Component
class CacheProvider(
    private val projectCacheService: BaseCacheService<ProjectResponse>,
    private val employeeCacheService: BaseCacheService<EmployeeResponse>,
) {

    fun getProject(projectId: Long): ProjectResponse {
        return projectCacheService.getByEntityId(projectId)
    }

    fun getEmployee(employeeId: Long): EmployeeResponse {
        return employeeCacheService.getByEntityId(employeeId)
    }

}