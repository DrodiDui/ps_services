package com.kapitonau.ps.aggregatorservice.config

import com.kapitonau.ps.aggregatorservice.service.processor.ModelEventDtoProcessor
import com.kapitonau.ps.apirequestlib.kafka.ModelType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Function
import java.util.stream.Collectors

@Configuration
class ModelEventDtoProcessorConfig {


    @Bean("processorsMap")
    fun modelEventDtoProcessors(processors: MutableList<ModelEventDtoProcessor?>): MutableMap<ModelType?, ModelEventDtoProcessor?> {
        return processors.stream().collect(
            Collectors.toMap(
                Function { obj: ModelEventDtoProcessor? -> obj!!.getModelType() },
                Function.identity<ModelEventDtoProcessor?>()
            )
        )
    }
}
