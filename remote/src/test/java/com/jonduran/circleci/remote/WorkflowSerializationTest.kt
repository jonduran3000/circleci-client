package com.jonduran.circleci.remote

import com.google.common.truth.Truth.assertThat
import com.jonduran.circleci.remote.model.Workflow
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@UnstableDefault
@ImplicitReflectionSerializer
class WorkflowSerializationTest {
    @Test
    @DisplayName("Serialize Workflow object into JSON")
    fun workflowSerialization() {
        val workflow = TestWorkflowFactory.create()

        val actual = Json.indented.stringify(workflow)

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

        val actual = Json.indented.parse(Workflow.serializer(), json)

        assertThat(actual).isEqualTo(expected)
    }
}