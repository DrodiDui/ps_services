package com.kapitonau.taskservice.cache

import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse
import com.kapitonau.taskservice.client.EmployeeApiClient
import org.springframework.stereotype.Component

@Component
class EmployeeCache(
    private val employeeApiClient: EmployeeApiClient
) {

    private val EMPLOYEE_CACHE = mutableMapOf<Long, EmployeeResponse>()

    fun getEmployee(employeeId: Long): EmployeeResponse {
        if (EMPLOYEE_CACHE.containsKey(employeeId)) {
            return EMPLOYEE_CACHE[employeeId]!!
        } else {
            val response = employeeApiClient.getEmployeeById(employeeId)
            EMPLOYEE_CACHE.put(employeeId, response)
            return response;
        }
    }

}