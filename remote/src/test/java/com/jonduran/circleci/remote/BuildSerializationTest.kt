package com.jonduran.circleci.remote

import com.google.common.truth.Truth.assertThat
import com.jonduran.circleci.remote.model.Build
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID

@ImplicitReflectionSerializer
class BuildSerializationTest {

    companion object {
        private val JSON = Json(
            JsonConfiguration.Stable.copy(
                prettyPrint = true,
                useArrayPolymorphism = true
            )
        )
    }


    @Test
    @DisplayName("Serialize Build object into JSON")
    fun buildSerialization() {
        val username = "jonduran3000"
        val reponame = "circleci-client"
        val buildUrl = "https://circleci.com/gh/jonduran3000/circleci-client/1"
        val branch = "master"
        val gitSha = UUID.randomUUID().toString()
        val committerDate = Instant.now()
        val buildNumber = 1L
        val status = "success"
        val subject = "Initial commit"
        val queuedAt = Instant.now()
        val startTime = Instant.now()
        val stopTime = Instant.now()
        val buildTimeInMillis = Duration.between(startTime, stopTime).toMillis()
        val workflow = TestWorkflowFactory.create()
        val user = TestUserFactory.create()

        val build = Build(
            username = username,
            reponame = reponame,
            buildUrl = buildUrl,
            branch = branch,
            gitSha = gitSha,
            committerDate = committerDate,
            buildNumber = buildNumber,
            status = status,
            subject = subject,
            queuedAt = queuedAt,
            startTime = startTime,
            stopTime = stopTime,
            buildTimeInMillis = buildTimeInMillis,
            workflow = workflow,
            user = user
        )

        val actual = JSON.stringify(build)

        val expected = """
            {
                "username": "${build.username}",
                "reponame": "${build.reponame}",
                "build_url": "${build.buildUrl}",
                "branch": "${build.branch}",
                "vcs_revision": "${build.gitSha}",
                "committer_date": "${DateTimeFormatter.ISO_INSTANT.format(build.committerDate)}",
                "build_num": ${build.buildNumber},
                "status": "${build.status}",
                "subject": "${build.subject}",
                "queued_at": "${DateTimeFormatter.ISO_INSTANT.format(build.queuedAt)}",
                "start_time": "${DateTimeFormatter.ISO_INSTANT.format(build.startTime)}",
                "stop_time": "${DateTimeFormatter.ISO_INSTANT.format(build.stopTime)}",
                "build_time_millis": ${build.buildTimeInMillis},
                "workflows": {
                    "job_id": "${build.workflow.jobId}",
                    "job_name": "${build.workflow.jobName}",
                    "workflow_id": "${build.workflow.workflowId}",
                    "workflow_name": "${build.workflow.workflowName}"
                },
                "user": {
                    "login": "${build.user.login}",
                    "name": "${build.user.name}",
                    "avatar_url": "${build.user.avatarUrl}"
                }
            }
            """.trimIndent()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("Deserialize JSON into Build object")
    fun buildDeserialization() {
        val username = "jonduran3000"
        val reponame = "circleci-client"
        val buildUrl = "https://circleci.com/gh/jonduran3000/circleci-client/1"
        val branch = "master"
        val gitSha = UUID.randomUUID().toString()
        val committerDate = Instant.now()
        val buildNumber = 1L
        val status = "success"
        val subject = "Initial commit"
        val queuedAt = Instant.now()
        val startTime = Instant.now()
        val stopTime = Instant.now()
        val buildTimeInMillis = Duration.between(startTime, stopTime).toMillis()
        val workflow = TestWorkflowFactory.create()
        val user = TestUserFactory.create()

        val expected = Build(
            username = username,
            reponame = reponame,
            buildUrl = buildUrl,
            branch = branch,
            gitSha = gitSha,
            committerDate = committerDate,
            buildNumber = buildNumber,
            status = status,
            subject = subject,
            queuedAt = queuedAt,
            startTime = startTime,
            stopTime = stopTime,
            buildTimeInMillis = buildTimeInMillis,
            workflow = workflow,
            user = user
        )

        val json = """
            {
                "username": "${expected.username}",
                "reponame": "${expected.reponame}",
                "build_url": "${expected.buildUrl}",
                "branch": "${expected.branch}",
                "vcs_revision": "${expected.gitSha}",
                "committer_date": "${DateTimeFormatter.ISO_INSTANT.format(expected.committerDate)}",
                "build_num": ${expected.buildNumber},
                "status": "${expected.status}",
                "subject": "${expected.subject}",
                "queued_at": "${DateTimeFormatter.ISO_INSTANT.format(expected.queuedAt)}",
                "start_time": "${DateTimeFormatter.ISO_INSTANT.format(expected.startTime)}",
                "stop_time": "${DateTimeFormatter.ISO_INSTANT.format(expected.stopTime)}",
                "build_time_millis": ${expected.buildTimeInMillis},
                "workflows": {
                    "job_id": "${expected.workflow.jobId}",
                    "job_name": "${expected.workflow.jobName}",
                    "workflow_id": "${expected.workflow.workflowId}",
                    "workflow_name": "${expected.workflow.workflowName}"
                },
                "user": {
                    "login": "${expected.user.login}",
                    "name": "${expected.user.name}",
                    "avatar_url": "${expected.user.avatarUrl}"
                }
            }
            """.trimIndent()

        val actual = JSON.parse(Build.serializer(), json)

        assertThat(actual).isEqualTo(expected)
    }
}