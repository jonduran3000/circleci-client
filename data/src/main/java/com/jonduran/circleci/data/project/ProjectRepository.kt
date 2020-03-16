package com.jonduran.circleci.data.project

import com.jonduran.circleci.cache.entity.ProjectEntity
import com.jonduran.circleci.cache.entity.SourceControl
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val remoteSource: RemoteProjectDataSource,
    private val localSource: LocalProjectDataSource
) {
    private var invalidCache = false

    suspend fun getListOfProjects(
        sourceControl: Array<SourceControl>,
        username: String
    ): List<ProjectEntity> {
        val cache = localSource.getListOfProjects(sourceControl, username)
        if (cache.isNotEmpty() && !invalidCache) {
            return cache
        }

        val remoteList = remoteSource.getListOfProjects(sourceControl, username)
        localSource.saveAll(remoteList)
        return localSource.getListOfProjects(sourceControl, username).also { invalidCache = false }
    }

    suspend fun getListOfProjectOwners(): List<String> {
        return localSource.getListOfProjectOwners()
    }

    fun invalidateCache() {
        invalidCache = true
    }
}