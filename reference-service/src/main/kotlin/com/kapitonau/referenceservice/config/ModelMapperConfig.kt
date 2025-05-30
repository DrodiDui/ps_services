package com.kapitonau.referenceservice.config

import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean

@org.springframework.context.annotation.Configuration
class ModelMapperConfig {

    @Bean(name = ["modelMapper"])
    fun modelMapper(): ModelMapper {
        val mapper = ModelMapper()
        mapper.configuration
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true).fieldAccessLevel = Configuration.AccessLevel.PRIVATE
        return mapper
    }
}
