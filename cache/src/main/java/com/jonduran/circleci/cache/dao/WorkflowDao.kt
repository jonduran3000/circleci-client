package com.jonduran.circleci.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.jonduran.circleci.cache.entity.WorkflowEntity

@Dao
interface WorkflowDao {
    @Query("""
        SELECT 
            workflowId AS id, 
			(username || "\" || reponame) as slub,
            workflowName AS name, 
			branch,
			subject,
			gitSha,
			MIN(startTime) AS startTime,
            SUM(buildTimeInMillis) AS totalBuildTimeInMillis 
        FROM 
            BuildEntity 
        GROUP BY 
            workflowId, subject, gitSha
		ORDER BY
			queuedAt
		DESC;
    """)
    suspend fun getWorkflows(): List<WorkflowEntity>
}