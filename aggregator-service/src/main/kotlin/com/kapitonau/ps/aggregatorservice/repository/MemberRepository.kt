package com.kapitonau.ps.aggregatorservice.repository

import com.kapitonau.ps.aggregatorservice.model.MemberModel
import com.kapitonau.ps.aggregatorservice.repository.custom.CustomMemberRepository
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<MemberModel, Long>, CustomMemberRepository {



}
