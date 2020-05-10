package com.jonduran.circleci.remote

import com.jonduran.circleci.remote.model.Build
import com.jonduran.circleci.remote.model.Project
import com.jonduran.circleci.remote.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface CircleCiV1Api {
    @GET("me")
    suspend fun getUser(): User

    @GET("projects")
    suspend fun getListOfProjects(): List<Project>

    @GET("recent-builds")
    suspend fun getRecentBuilds(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("shallow") shallow: Boolean = true,
        @Query("mine") mine: Boolean = false
    ): List<Build>
}