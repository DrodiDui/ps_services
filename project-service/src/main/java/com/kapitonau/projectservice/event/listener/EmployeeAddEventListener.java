package com.kapitonau.projectservice.event.listener;

import com.kapitonau.projectservice.event.sender.AddUserToSpaceEventProducer;
import com.kapitonau.projectservice.model.EmployeeModel;
import com.kapitonau.projectservice.model.SpaceEmployeeModel;
import com.kapitonau.projectservice.model.SpaceEmployeeModelPk;
import com.kapitonau.projectservice.model.SpaceModel;
import com.kapitonau.projectservice.repository.EmployeeRepository;
import com.kapitonau.projectservice.repository.SpaceEmployeeRepository;
import com.kapitonau.projectservice.repository.SpaceRepository;
import com.kapitonau.projectstudio.bean.cache.ReferenceCache;
import com.kapitonau.projectstudio.event.AddUserToSpaceEventDto;
import com.kapitonau.projectstudio.event.UserAddEventDto;
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import com.kapitonau.projectstudio.referenceservice.dto.ReferenceItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeAddEventListener {

    public static final Long MAINTAINER_ROLE_ID = 1L;

    private final ModelMapper modelMapper;
    private final ReferenceCache referenceCache;
    private final EmployeeRepository employeeRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceEmployeeRepository spaceEmployeeRepository;
    private final AddUserToSpaceEventProducer addUserToSpaceEventProducer;

    @Value("${kafka.topic.user-add-event.sleepTimeMs}")
    private Long slipTimeMs;

    @Transactional(transactionManager = "projectPlatformTransactionManager")
    @KafkaListener(topics = "${kafka.topic.user-add-event.name}", groupId = "${kafka.topic.user-add-event.groupId}")
    public void listen(@Payload UserAddEventDto message, @Header(KafkaHeaders.OFFSET) Long offset, Acknowledgment ack) {
        log.info("Received message from user add event topic. Offset: {}, data: {}", offset, message);
        try {
            EmployeeModel model = modelMapper.map(message, EmployeeModel.class);
            model.setEmployeeStatusId(referenceCache.getReferenceItemByTypeAndCode("EMPLOYEE_STATUS", "ACTIVE").referenceItemId());
            employeeRepository.save(model);

            SpaceModel spaceMode = SpaceModel.builder()
                    .title(model.getEmail().substring(0, model.getEmail().indexOf("@")) + "-space")
                    .description("")
                    .build();
            spaceMode = spaceRepository.save(spaceMode);

            SpaceEmployeeModel entity = SpaceEmployeeModel.builder()
                    .spaceEmployeeId(new SpaceEmployeeModelPk(spaceMode.getSpaceId(), model.getEmployeeId()))
                    .employeeSpaceRole(MAINTAINER_ROLE_ID)
                    .build();
            SpaceEmployeeModel spaceEmployeeModel = spaceEmployeeRepository.save(entity);

            addUserToSpaceEventProducer.send(new AddUserToSpaceEventDto(
                    spaceEmployeeModel.getSpaceEmployeeId().getEmployeeId(),
                    spaceEmployeeModel.getSpaceEmployeeId().getSpaceId(),
                    MAINTAINER_ROLE_ID
            ));

            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ack.nack(Duration.ofMillis(slipTimeMs));
        }
    }

}
