package com.jonduran.circleci.data

import com.jonduran.circleci.cache.UserEntity

interface UserDataSource {
    suspend fun getCurrentUser(): UserEntity
}