package com.kapitonau.ps.taskservice.client

import com.kapitonau.ps.apirequestlib.workspaces.api.MemberApi
import com.kapitonau.ps.commonspringlib.feign.JwtLocaleFeignConfig
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "workspace-service", contextId = "MemberApiClient-contextId", url = "\${api.workspace-service.url}", configuration = [JwtLocaleFeignConfig::class])
interface MemberApiClient: MemberApi {
}