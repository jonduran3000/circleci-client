package com.jonduran.circleci.data.build

import com.jonduran.circleci.cache.entity.BuildEntity
import com.jonduran.circleci.cache.entity.Status
import com.jonduran.circleci.remote.CircleCiApi
import javax.inject.Inject

class RemoteBuildDataSource @Inject constructor(private val api: CircleCiApi) : BuildDataSource {
    override suspend fun getRecentBuilds(): List<BuildEntity> {
        return api.getRecentBuilds(0, 30).map { build ->
            BuildEntity(
                username = build.username,
                reponame = build.reponame,
                buildUrl = build.buildUrl,
                branch = build.branch,
                gitSha = build.gitSha,
                committerDate = build.committerDate,
                buildNumber = build.buildNumber,
                status = Status.valueOf(build.status),
                subject = build.subject,
                queuedAt = build.queuedAt,
                startTime = build.startTime,
                stopTime = build.stopTime,
                buildTimeInMillis = build.buildTimeInMillis ?: 0L,
                jobId = build.workflow.jobId,
                jobName = build.workflow.jobName,
                workflowId = build.workflow.workflowId,
                workflowName = build.workflow.workflowName,
                login = build.user.login,
                name = build.user.name,
                avatarUrl = build.user.avatarUrl
            )
        }
    }

    override suspend fun save(build: BuildEntity) {
        throw UnsupportedOperationException()
    }

    override suspend fun saveAll(builds: List<BuildEntity>) {
        throw UnsupportedOperationException()
    }
}