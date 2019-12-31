package com.jonduran.circleci.data

import com.jonduran.circleci.cache.ProjectEntity
import com.jonduran.circleci.cache.SourceControl
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteSource: RemoteProjectDataSource,
    private val localSource: LocalProjectDataSource,
    private val store: Lazy<DataStore>
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

    suspend fun storeKey(key: String) = withContext(Dispatchers.Default) {
        val credentials = Credentials.basic(key, "")
        store.get().setCredentials(credentials)
    }

    suspend fun hasCredentials() = withContext(Dispatchers.Default) {
        store.get().hasCredentials()
    }

    fun invalidateCache() {
        invalidCache = true
    }
}