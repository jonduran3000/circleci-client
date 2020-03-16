package com.jonduran.circleci.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val name: String,
    @SerialName("avatar_url") val avatarUrl: String
)