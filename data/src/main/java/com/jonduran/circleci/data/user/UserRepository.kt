package com.jonduran.circleci.data.user

import com.jonduran.circleci.cache.entity.UserEntity
import com.jonduran.circleci.data.DataStore
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteSource: RemoteUserDataSource,
    private val localSource: LocalUserDataSource,
    private val store: Lazy<DataStore>
) {
    suspend fun getCurrentUser(): UserEntity {
        return remoteSource.getCurrentUser()
    }

    suspend fun storeKey(key: String) = withContext(Dispatchers.Default) {
        val credentials = Credentials.basic(key, "")
        store.get().setCredentials(credentials)
    }

    suspend fun deleteKey() = withContext(Dispatchers.Default) {
        store.get().deleteCredentials()
    }

    suspend fun hasCredentials() = withContext(Dispatchers.Default) {
        store.get().hasCredentials()
    }
}