package com.jonduran.circleci.remote

import com.google.common.truth.Truth.assertThat
import com.jonduran.circleci.remote.model.Workflow
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ImplicitReflectionSerializer
class WorkflowSerializationTest {
    companion object {
        private val JSON = Json(
            JsonConfiguration.Stable.copy(
                prettyPrint = true,
                useArrayPolymorphism = true
            )
        )
    }

    @Test
    @DisplayName("Serialize Workflow object into JSON")
    fun workflowSerialization() {
        val workflow = TestWorkflowFactory.create()

        val actual = JSON.stringify(workflow)

        val expected = """
            {
                "job_id": "${workflow.jobId}",
                "job_name": "${workflow.jobName}",
                "workflow_id": "${workflow.workflowId}",
                "workflow_name": "${workflow.workflowName}"
            }
            """.trimIndent()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("Deserialize JSON into Workflow object")
    fun workflowDeserialization() {
        val expected = TestWorkflowFactory.create()

        val json = """
            {
                "job_id": "${expected.jobId}",
                "job_name": "${expected.jobName}",
                "workflow_id": "${expected.workflowId}",
                "workflow_name": "${expected.workflowName}"
            }
            """.trimIndent()

        val actual = JSON.parse(Workflow.serializer(), json)

        assertThat(actual).isEqualTo(expected)
    }
}