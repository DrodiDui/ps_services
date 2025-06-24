package com.kapitonau.ps.workspaceservice.event.sender

import com.kapitonau.ps.apirequestlib.kafka.UserWorkspaceEventMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MemberWorkspaceCreateEventSender(
    private val kafkaTemplate: KafkaTemplate<String, UserWorkspaceEventMessage>,
    @Value("\${kafka-topics.user-workspace-role-event.name}") private val topic: String
) {

    fun send(message: UserWorkspaceEventMessage) {
        kafkaTemplate.send(topic, message)
    }

}