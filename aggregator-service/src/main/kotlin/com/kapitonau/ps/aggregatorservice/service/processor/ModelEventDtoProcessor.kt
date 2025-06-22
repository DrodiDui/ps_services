package com.kapitonau.ps.aggregatorservice.service.processor

import com.kapitonau.ps.apirequestlib.kafka.ModelEventDto
import com.kapitonau.ps.apirequestlib.kafka.ModelType

interface ModelEventDtoProcessor {

    fun process(message: ModelEventDto)

    fun getModelType(): ModelType

}