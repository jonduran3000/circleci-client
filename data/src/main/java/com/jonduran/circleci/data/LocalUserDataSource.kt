package com.jonduran.circleci.data

import com.jonduran.circleci.cache.AppDatabase
import com.jonduran.circleci.cache.UserEntity
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(private val database: AppDatabase) : UserDataSource {
    override suspend fun getCurrentUser(): UserEntity {
        throw UnsupportedOperationException()
    }
}