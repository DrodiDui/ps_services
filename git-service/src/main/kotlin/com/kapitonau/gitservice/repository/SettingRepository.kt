package com.kapitonau.gitservice.repository

import com.kapitonau.gitservice.model.SettingModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SettingRepository: JpaRepository<SettingModel, Long> {

    fun findByRepository_RepositoryId(repositoryId: Long): Optional<SettingModel>

}