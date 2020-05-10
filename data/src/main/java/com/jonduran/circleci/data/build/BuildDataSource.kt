package com.jonduran.circleci.data.build

import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import com.jonduran.circleci.cache.AppDatabase
import com.jonduran.circleci.cache.entity.BuildEntity
import com.jonduran.circleci.cache.entity.Status
import com.jonduran.circleci.data.DataSource
import com.jonduran.circleci.remote.CircleCiV1Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BuildDataSource @Inject constructor(
    api: CircleCiV1Api,
    database: AppDatabase
) : DataSource<Unit, List<BuildEntity>> {
    private val store = StoreBuilder.fromNonFlow<Unit, List<BuildEntity>> {
        api.getRecentBuilds(0, 30).map { build ->
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
    }.persister(
        reader = { database.buildDao().getBuilds() },
        writer = { _, builds -> database.buildDao().insertAll(builds) }
    ).build()

    override fun stream(key: Unit): Flow<List<BuildEntity>> {
        return store.stream(StoreRequest.cached(key, true))
            .map { result -> result.requireData() }
    }

    override suspend fun get(key: Unit): List<BuildEntity> = store.get(key)

    override suspend fun fresh(key: Unit): List<BuildEntity> = store.fresh(key)

    override suspend fun clear(key: Unit) {
        store.clear(key)
    }
}