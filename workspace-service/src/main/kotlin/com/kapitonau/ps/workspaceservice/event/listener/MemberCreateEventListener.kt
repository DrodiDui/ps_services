package com.kapitonau.ps.workspaceservice.event.listener

import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.kafka.MemberCreateEventMessage
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelEventDtoConverter
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import com.kapitonau.ps.apirequestlib.workspaces.dto.WorkspacePostRequest
import com.kapitonau.ps.workspaceservice.event.sender.ModelEventSender
import com.kapitonau.ps.workspaceservice.model.MemberModel
import com.kapitonau.ps.workspaceservice.repository.MemberRepository
import com.kapitonau.ps.workspaceservice.service.WorkspaceMemberService
import com.kapitonau.ps.workspaceservice.service.WorkspaceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Component
class MemberCreateEventListener(
    private val memberRepository: MemberRepository,
    private val modelEventSender: ModelEventSender,
    private val modelEventDtoConverter: ModelEventDtoConverter,
    private val referenceCache: ReferenceCache,
    private val workspaceService: WorkspaceService,
    private val workspaceMemberService: WorkspaceMemberService,
    @Value("\${kafka-topics.member-create-event.name}") private val kafkaTopic: String,
) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @Transactional("workspaceTransactionManager")
    @KafkaListener(topics = ["\${kafka-topics.member-create-event.name}"], groupId = "\${kafka-topics.member-create-event.groupId}")
    fun listen(@Payload message: MemberCreateEventMessage, @Header(KafkaHeaders.OFFSET) offset: Long, ack: Acknowledgment) {
        try {
            logger.info("Received message from topic: {} with offset: {}. Data: {}", kafkaTopic, offset, message)

            val model = MemberModel()
            model.memberId = message.memberId
            model.username = message.username
            model.email = message.email
            model.firstName = message.firstName
            model.lastName = message.lastName
            model.middleName = message.middleName
            model.memberRoleId = referenceCache.getReferenceItemByTypeAndCode("MEMBER_POSITION", message.memberPositionCode).referenceItemId
            model.memberStatusId = referenceCache.getReferenceItemByTypeAndCode("MEMBER_STATUS", "ACTIVE").referenceItemId
            model.createdDate = message.createDate
            model.createdBy = message.createdBy
            model.lastModifiedDate = message.lastModifiedDate
            model.lastModifiedBy = message.lastModifiedBy
            /*model.version = 1*/
            model.isDeleted = false

            memberRepository.save(model)

            modelEventSender.send(ModelEventDto(
                ModelType.MEMBER,
                modelEventDtoConverter.toMap(model)
            ))

            val createWorkspace = workspaceService.createWorkspaceWithOwner(
                WorkspacePostRequest(
                    "${model.username}-workspace",
                    "It's your workspace"
                ),
                model.memberId!!
            )

            workspaceMemberService.createMaintainer(
                model.memberId!!,
                createWorkspace.workspaceId,
                message.roleId
                );



            ack.acknowledge()
        } catch (ex: Exception) {
            logger.error("Error while receiving message", ex)
            ack.nack(Duration.ofMillis(60_000))
        }
    }

}
