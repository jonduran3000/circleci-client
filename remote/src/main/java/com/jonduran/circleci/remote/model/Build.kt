package com.jonduran.circleci.remote.model

import com.jonduran.circleci.remote.serializers.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.threeten.bp.Instant

@Serializable
data class Build(
    val username: String,
    val reponame: String,
    @SerialName("build_url") val buildUrl: String,
    val branch: String?,
    @SerialName("vcs_revision") val gitSha: String,
    @SerialName("committer_date")
    @Serializable(with = InstantSerializer::class)
    val committerDate: Instant?,
    @SerialName("build_num") val buildNumber: Long,
    val status: String,
    val subject: String?,
    @SerialName("queued_at")
    @Serializable(with = InstantSerializer::class)
    val queuedAt: Instant?,
    @SerialName("start_time")
    @Serializable(with = InstantSerializer::class)
    val startTime: Instant?,
    @SerialName("stop_time")
    @Serializable(with = InstantSerializer::class)
    val stopTime: Instant?,
    @SerialName("build_time_millis") val buildTimeInMillis: Long,
    @SerialName("workflows") val workflow: Workflow,
    val user: User
)