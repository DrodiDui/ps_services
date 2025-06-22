package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.TagModel
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<TagModel, Long> {


}