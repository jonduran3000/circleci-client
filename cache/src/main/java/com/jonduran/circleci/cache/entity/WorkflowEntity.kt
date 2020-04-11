package com.jonduran.circleci.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.UUID

@Entity
data class WorkflowEntity(
    @PrimaryKey val id: UUID,
    val slub: String,
    val name: String,
    val branch: String?,
    val subject: String?,
    val gitSha: String,
    val startTime: Instant?,
    val totalBuildTimeInMillis: Long
)