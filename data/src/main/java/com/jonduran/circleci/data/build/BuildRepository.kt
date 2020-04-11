package com.jonduran.circleci.data.build

import com.jonduran.circleci.cache.entity.BuildEntity
import javax.inject.Inject

class BuildRepository @Inject constructor(
    private val dataSource: BuildDataSource
) {
    suspend fun getRecentBuilds(): List<BuildEntity> {
        return dataSource.fresh(Unit)
    }
}