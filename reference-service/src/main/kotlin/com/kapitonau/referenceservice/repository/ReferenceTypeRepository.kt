package com.kapitonau.referenceservice.repository

import com.kapitonau.referenceservice.model.ReferencesTypeModel
import org.springframework.data.jpa.repository.JpaRepository

interface ReferenceTypeRepository : JpaRepository<ReferencesTypeModel, String> {
}