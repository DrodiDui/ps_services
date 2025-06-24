package com.kapitonau.ps.workspaceservice.service.base

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse
import com.kapitonau.ps.commonspringlib.exception.CommonServiceException
import com.kapitonau.ps.workspaceservice.repository.MemberRepository
import com.kapitonau.ps.workspaceservice.service.MemberService
import org.springframework.stereotype.Service

@Service
class BaseMemberService(
    private val memberRepository: MemberRepository,
    private val referenceCache: ReferenceCache
) : MemberService {


    override fun getMemberById(memberId: Long): MemberResponse {
        return memberRepository.findById(memberId)
            .map { it -> MemberResponse(
                it.memberId,
                it.username,
                it.email,
                it.firstName,
                it.lastName,
                it.middleName,
                referenceCache.getReferenceItemById(it.memberRoleId),
                referenceCache.getReferenceItemById(it.memberStatusId)
            ) }
            .orElseThrow { CommonServiceException("WORKSPACE_SERVICE", "Member not found") }
    }


}