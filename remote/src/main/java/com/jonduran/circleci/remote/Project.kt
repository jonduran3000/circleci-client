package com.jonduran.circleci.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    @SerialName("reponame") val name: String,
    val username: String,
    @SerialName("vcs_type") val type: String,
    @SerialName("vcs_url") val url: String
)