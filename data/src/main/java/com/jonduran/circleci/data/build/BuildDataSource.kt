package com.jonduran.circleci.data.build

import com.jonduran.circleci.cache.entity.BuildEntity

interface BuildDataSource {
    suspend fun getRecentBuilds(): List<BuildEntity>
    suspend fun save(build: BuildEntity)
    suspend fun saveAll(builds: List<BuildEntity>)
}