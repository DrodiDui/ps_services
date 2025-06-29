package com.kapitonau.ps.aggregatorservice.repository.custom.impl

import com.kapitonau.ps.aggregatorservice.repository.custom.CustomTaskRepository
import com.kapitonau.ps.apirequestlib.aggregate.dto.task.TaskStatusResponse
import com.kapitonau.ps.apirequestlib.bean.cache.ReferenceCache
import com.kapitonau.ps.apirequestlib.common.tasks.TaskResponse
import com.kapitonau.ps.apirequestlib.common.workspaces.ProjectResponse
import com.kapitonau.ps.apirequestlib.common.workspaces.WorkspaceResponse
import com.kapitonau.ps.apirequestlib.workspaces.dto.MemberResponse
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Repository
class CustomTaskAggregatorRepository(
    @PersistenceContext private val entityManager: EntityManager,
    private val referenceCache: ReferenceCache
) : CustomTaskRepository {


    override fun countTasksByFilters(workspaceId: Long, projects: List<Long>?): Long {

        val query = """
            select count(t.task_id)
from tasks t
         join projects p on p.project_id = t.project_id
         join workspaces w on w.workspace_id = t.workspace_id and w.workspace_id = p.workspace_id
         join members wo on wo.member_id = w.owner_id
         join members t_owner on t_owner.member_id = t.owner_id
         join members ta on ta.member_id = t.assignee_id
         left join task_tags tt on tt.task_id = t.task_id
         left join tags ts on ts.tag_id = tt.tag_id
where w.workspace_id = :workspaceId
  ${if (projects != null && projects.isNotEmpty()) "and p.project_id in (:projects)" else ""}
        """.trimIndent()
        val nativeQuery = entityManager.createNativeQuery(query)

        nativeQuery.setParameter("workspaceId", workspaceId)
        if (projects != null && projects.isNotEmpty())
            nativeQuery.setParameter("projects", projects)

        return (nativeQuery.singleResult as Number).toLong()
    }

    override fun findAllTasksByFilters(
        workspaceId: Long,
        projects: List<Long>?,
        offset: Long,
        limit: Long
    ): List<TaskResponse> {

        val query = """
            select t.task_id            as taskId,
       t.task_name          as taskName,
       t.task_title         as taskTitle,
       t.description        as taskDescription,
       t.task_status_id     as taskStatusId,
       t.task_priority_id   as taskPriorityId,
       t.task_type_id       as taskTypeId,
       p.project_id         as projectId,
       p.project_title      as projectName,
       p.description        as projectDescription,
       p.created_date       as p_createdDate,
       p.project_type_id    as projectTypeId,
       w.workspace_id       as workspaceId,
       w.workspace_name     as workspaceName,
       w.description        as w_description,
       w.created_date       as w_createdDate,
       t_owner.member_id as ownerMemberId,
       t_owner.username as ownerUsername,
       t_owner.email as ownerEmail,
       t_owner.first_name as ownerFirstName,
       t_owner.last_name as ownerLastName,
       t_owner.middle_name as ownerMiddleName,
       t_owner.member_role_id as ownerPositionId,
       t_owner.member_status_id as ownerStatusId,
       ta.member_id as assigneeMemberId,
       ta.username as assigneeUsername,
       ta.email as assigneeEmail,
       ta.first_name as assigneeFirstName,
       ta.last_name as assigneeLastName,
       ta.middle_name as assigneeMiddleName,
       ta.member_role_id as assigneePositionId,
       ta.member_status_id as assigneeStatusId,
       t.created_date       as creadedDate,
       t.last_modified_date as lastModifiedDate,
       p.git_provider_id as gitProviderId
from tasks t
         join projects p on p.project_id = t.project_id
         join workspaces w on w.workspace_id = t.workspace_id and w.workspace_id = p.workspace_id
         join members wo on wo.member_id = w.owner_id
         join members t_owner on t_owner.member_id = t.owner_id
         join members ta on ta.member_id = t.assignee_id
         left join task_tags tt on tt.task_id = t.task_id
         left join tags ts on ts.tag_id = tt.tag_id
where w.workspace_id = :workspaceId
  ${if (projects != null && projects.isNotEmpty()) "and p.project_id in (:projects)" else ""}
        """.trimIndent()


        val nativeQuery = entityManager.createNativeQuery(query)
        nativeQuery.setParameter("workspaceId", workspaceId)
        if (projects != null && projects.isNotEmpty()) {
            nativeQuery.setParameter("projects", projects)
        }

        nativeQuery.firstResult = offset.toInt()
        nativeQuery.maxResults = limit.toInt()


        val results = nativeQuery.resultList as List<Array<Any>>
        return results.map { row -> mapRowToTaskResponse(row) }
    }

    override fun findAllTasksByFiltersGroupByStatus(
        workspaceId: Long,
        projects: List<Long>?
    ): List<TaskStatusResponse> {
        TODO("Not yet implemented")
    }

    private fun mapRowToTaskResponse(row: Array<Any>): TaskResponse {
        return TaskResponse(
            row[0] as Long,
            row[1] as String,
            row[2] as String,
            row[3] as String,
            referenceCache.getReferenceItemById(row[4] as Long),
            referenceCache.getReferenceItemById(row[5] as Long),
            referenceCache.getReferenceItemById(row[6] as Long),
            ProjectResponse(
                row[7] as Long,
                row[8] as String,
                row[9] as String,
                ZonedDateTime.ofInstant(row[10] as Instant, ZoneId.systemDefault()),
                referenceCache.getReferenceItemById(row[11] as Long),
                if (row[34] !== null) referenceCache.getReferenceItemById(row[34] as Long) else null,
            ),
            WorkspaceResponse(
                row[12] as Long,
                row[13] as String,
                row[14] as String,
                ZonedDateTime.ofInstant(row[15] as Instant, ZoneId.systemDefault()),
                null
            ),
            MemberResponse(
                row[16] as Long,
                row[17] as String,
                row[18] as String,
                row[19] as String,
                row[20] as String,
                row[21] as? String,
                referenceCache.getReferenceItemById(row[22] as Long),
                referenceCache.getReferenceItemById(row[23] as Long),
            ),
            MemberResponse(
                row[24] as Long,
                row[25] as String,
                row[26] as String,
                row[27] as String,
                row[28] as String,
                row[29] as? String,
                referenceCache.getReferenceItemById(row[30] as Long),
                referenceCache.getReferenceItemById(row[31] as Long),
            ),
            /*convertToZonedDateTime(row[32])*/ ZonedDateTime.ofInstant(row[32] as Instant, ZoneId.systemDefault()),
            /*convertToZonedDateTime(row[33])*/ZonedDateTime.ofInstant(row[33] as Instant, ZoneId.systemDefault()),
            null
        )
    }

}