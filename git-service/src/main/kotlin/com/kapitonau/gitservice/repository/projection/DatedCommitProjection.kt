package com.kapitonau.gitservice.repository.projection

import com.kapitonau.gitservice.model.CommitModel
import java.time.ZonedDateTime

interface DatedCommitProjection {

    fun getCommitTime(): ZonedDateTime

    fun getCommit(): CommitModel

}