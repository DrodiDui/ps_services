package com.kapitonau.gitservice.repository

import com.kapitonau.gitservice.model.CommitModel
import com.kapitonau.gitservice.repository.projection.DatedCommitProjection
import com.kapitonau.projectstudio.gitservice.dto.commit.CommitResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CommitRepository: JpaRepository<CommitModel, Long> {
    fun findAllByBranchIdOrderByCommitTimeDesc(branchId: Long): List<CommitModel>

    @Query("""
        select c.commitTime as commitTime, c as commit from CommitModel c where c.branchId = :branchId
        order by c.commitTime desc
    """)
    fun findAllByBranchId(branchId: Long): List<DatedCommitProjection>


    @Query("select * from commits s where s.branch_id = :branchId order by s.commit_id desc limit 1", nativeQuery = true)
    fun getLastCommitByBranchId(branchId: Long): Optional<CommitModel>

}
