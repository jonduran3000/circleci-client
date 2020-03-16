package com.jonduran.circleci.data.user

import com.jonduran.circleci.cache.entity.UserEntity

interface UserDataSource {
    suspend fun getCurrentUser(): UserEntity
}