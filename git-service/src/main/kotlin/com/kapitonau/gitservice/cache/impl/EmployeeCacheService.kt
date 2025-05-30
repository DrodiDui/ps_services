package com.kapitonau.gitservice.cache.impl

import com.kapitonau.gitservice.cache.BaseCacheService
import com.kapitonau.gitservice.client.feign.EmployeeApiClient
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse
import org.springframework.stereotype.Service

@Service
class EmployeeCacheService(
    private val employeeApiClient: EmployeeApiClient
): BaseCacheService<EmployeeResponse> {

    private val cache: MutableMap<String, EmployeeResponse> = HashMap()

    override fun getByEntityId(entityId: Long): EmployeeResponse {
        if (cache.containsKey(entityId.toString())) {
            return cache[entityId.toString()]!!
        } else {
            val response = employeeApiClient.getEmployeeById(entityId)
            cache.put(entityId.toString(), response)
            return response
        }
    }
}