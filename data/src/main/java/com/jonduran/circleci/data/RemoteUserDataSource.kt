package com.jonduran.circleci.data

import com.jonduran.circleci.cache.UserEntity
import com.jonduran.circleci.remote.CircleCiApi
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(private val api: CircleCiApi) : UserDataSource {
    override suspend fun getCurrentUser(): UserEntity {
        val user = api.getUser()
        return UserEntity(
            login = user.login,
            name = user.name,
            avatarUrl = user.avatarUrl
        )
    }
}