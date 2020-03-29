package com.jonduran.circleci.remote.model

import com.jonduran.circleci.remote.serializers.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Build(
    val username: String,
    val reponame: String,
    @SerialName("build_url") val buildUrl: String,
    val branch: String? = null,
    @SerialName("vcs_revision") val gitSha: String,
    @SerialName("committer_date")
    @Serializable(with = InstantSerializer::class)
    val committerDate: Instant? = null,
    @SerialName("build_num") val buildNumber: Long,
    val status: String,
    val subject: String? = null,
    @SerialName("queued_at")
    @Serializable(with = InstantSerializer::class)
    val queuedAt: Instant? = null,
    @SerialName("start_time")
    @Serializable(with = InstantSerializer::class)
    val startTime: Instant? = null,
    @SerialName("stop_time")
    @Serializable(with = InstantSerializer::class)
    val stopTime: Instant? = null,
    @SerialName("build_time_millis") val buildTimeInMillis: Long? = null,
    @SerialName("workflows") val workflow: Workflow,
    val user: User
)