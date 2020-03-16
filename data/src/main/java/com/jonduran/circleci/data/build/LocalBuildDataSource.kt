package com.jonduran.circleci.data.build

import com.jonduran.circleci.cache.AppDatabase
import com.jonduran.circleci.cache.dao.BuildDao
import com.jonduran.circleci.cache.entity.BuildEntity
import javax.inject.Inject

class LocalBuildDataSource @Inject constructor(database: AppDatabase) : BuildDataSource {
    private val dao: BuildDao = database.buildDao()

    override suspend fun getRecentBuilds(): List<BuildEntity> {
        return dao.getBuilds()
    }

    override suspend fun save(build: BuildEntity) {
        dao.insert(build)
    }

    override suspend fun saveAll(builds: List<BuildEntity>) {
        dao.insertAll(builds)
    }
}