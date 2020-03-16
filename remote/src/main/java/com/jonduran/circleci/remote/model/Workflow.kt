package com.jonduran.circleci.remote.model

import com.jonduran.circleci.remote.serializers.UuidSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Workflow(
    @SerialName("job_id")
    @Serializable(with = UuidSerializer::class)
    val jobId: UUID,
    @SerialName("job_name") val jobName: String,
    @SerialName("workflow_id")
    @Serializable(with = UuidSerializer::class)
    val workflowId: UUID,
    @SerialName("workflow_name") val workflowName: String
)