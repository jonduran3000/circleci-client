package com.jonduran.circleci.data

import com.jonduran.circleci.cache.AppDatabase
import com.jonduran.circleci.cache.ProjectEntity
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.remote.CircleCiApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: CircleCiApi,
    private val database: AppDatabase
) {
    private var invalidCache = false

    suspend fun getListOfProjects(
        sourceControl: Array<SourceControl>,
        username: String
    ): List<ProjectEntity> {
        val dao = database.projectDao()
        val cache = dao.getProjects(username, sourceControl)
        if (cache.isNotEmpty() && !invalidCache) {
            return cache
        }

        val remoteList = api.getListOfProjects()
        val toCache = remoteList.map { remote ->
            ProjectEntity(
                name = remote.name,
                username = remote.username,
                type = SourceControl.valueOf(remote.type),
                url = remote.url
            )
        }
        dao.insertAll(toCache)
        return dao.getProjects(username, sourceControl)
    }

    suspend fun getListOfProjectOwners(): List<String> {
        return database.projectDao().getProjectOwnerNames()
    }

    fun invalidateCache() {
        invalidCache = true
    }
}