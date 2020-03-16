package com.jonduran.circleci.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import java.util.UUID

@Entity
data class BuildEntity(
    val username: String,
    val reponame: String,
    @PrimaryKey val buildUrl: String,
    val branch: String?,
    val gitSha: String,
    val committerDate: Instant?,
    val buildNumber: Long,
    val status: Status,
    val subject: String?,
    val queuedAt: Instant?,
    val startTime: Instant?,
    val stopTime: Instant?,
    val buildTimeInMillis: Long,
    val jobId: UUID,
    val jobName: String,
    val workflowId: UUID,
    val workflowName: String,
    val login: String,
    val name: String,
    val avatarUrl: String
)