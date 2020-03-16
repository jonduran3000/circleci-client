package com.jonduran.circleci.data.build

import com.jonduran.circleci.cache.entity.BuildEntity
import javax.inject.Inject

class BuildRepository @Inject constructor(
    private val localSource: LocalBuildDataSource,
    private val remoteSource: RemoteBuildDataSource
) {
    private var invalidCache = false

    suspend fun getRecentBuilds(): List<BuildEntity> {
        val cache = localSource.getRecentBuilds()
        if (cache.isNotEmpty() && !invalidCache) {
            return cache
        }

        val response = remoteSource.getRecentBuilds()
        localSource.saveAll(response)
        return localSource.getRecentBuilds().also { invalidCache = false }
    }

    fun invalidateCache() {
        invalidCache = true
    }
}