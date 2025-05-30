package com.kapitonau.taskservice.client

import com.kapitonau.commonspring.feign.interceptor.JwtLocaleRequestInterceptor
import com.kapitonau.projectstudio.projectservice.api.ProjectApi
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "project-service", contextId = "ProjectClientApi-contextId", url = "\${api.project-service.url}", configuration = [JwtLocaleRequestInterceptor::class])
interface ProjectApiClient : ProjectApi{
}