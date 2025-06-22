package com.kapitonau.ps.taskservice.client

import com.kapitonau.ps.apirequestlib.workspaces.api.WorkspaceApi
import com.kapitonau.ps.commonspringlib.feign.JwtLocaleFeignConfig
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "workspace-service", contextId = "WorkspaceApiClient-contextId", url = "\${api.workspace-service.url}", configuration = [JwtLocaleFeignConfig::class])
interface WorkspaceApiClient: WorkspaceApi {
}