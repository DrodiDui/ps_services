package com.kapitonau.ps.aggregatorservice.service

import com.kapitonau.ps.apirequestlib.aggregate.dto.PageableWorkspaceResponse

interface WorkspaceService {

    fun getAvailableWorkspaces(onlyOwned: Boolean, offset: Long, limit: Long): PageableWorkspaceResponse

}
