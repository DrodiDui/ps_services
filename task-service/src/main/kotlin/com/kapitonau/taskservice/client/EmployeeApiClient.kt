package com.kapitonau.taskservice.client

import com.kapitonau.commonspring.feign.interceptor.JwtLocaleRequestInterceptor
import com.kapitonau.projectstudio.projectservice.api.EmployeeApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "project-service", contextId = "EmployeeClientApi-contextId", url = "\${api.project-service.url}", configuration = [JwtLocaleRequestInterceptor::class])
interface EmployeeApiClient: EmployeeApi {
}