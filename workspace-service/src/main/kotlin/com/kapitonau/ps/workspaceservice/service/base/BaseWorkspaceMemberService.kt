package com.kapitonau.ps.workspaceservice.service.base

import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.kafka.UserWorkspaceEventMessage
import com.kapitonau.ps.workspaceservice.event.sender.MemberWorkspaceCreateEventSender
import com.kapitonau.ps.workspaceservice.event.sender.ModelEventSender
import com.kapitonau.ps.workspaceservice.model.WorkspaceMemberModel
import com.kapitonau.ps.workspaceservice.model.WorkspaceMemberModelId
import com.kapitonau.ps.workspaceservice.repository.WorkspaceMemberRepository
import com.kapitonau.ps.workspaceservice.service.WorkspaceMemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class BaseWorkspaceMemberService(
    private val workspaceMemberRepository: WorkspaceMemberRepository,
    private val memberWorkspaceCreateEventSender: MemberWorkspaceCreateEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter,
    private val modelEventDtoSender: ModelEventSender,
) : WorkspaceMemberService {

    @Transactional("workspaceTransactionManager")
    override fun createMaintainer(memberId: Long, workspaceId: Long, roleId: Long) {

        val workspaceMember = WorkspaceMemberModel()
        val workspaceMemberModelId = WorkspaceMemberModelId()
        workspaceMemberModelId.memberId = memberId
        workspaceMemberModelId.workspaceId = workspaceId
        workspaceMember.workspaceMemberId = workspaceMemberModelId
        workspaceMember.roleId = roleId
        val currentTime = ZonedDateTime.now()
        workspaceMember.createdDate = currentTime

        workspaceMemberRepository.save(workspaceMember)


        memberWorkspaceCreateEventSender.send(UserWorkspaceEventMessage(memberId, workspaceId, roleId, currentTime))
        modelEventDtoSender.send(ModelEventDto(
            ModelType.WORKSPACE_MEMBER,
            modelEventDtoConverter.toMap(workspaceMember),
        ))


    }
}