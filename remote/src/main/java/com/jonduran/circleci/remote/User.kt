package com.jonduran.circleci.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    @SerialName("avatar_url") val avatarUrl: String
)