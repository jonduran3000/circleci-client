package com.jonduran.circleci.remote

import retrofit2.http.GET

interface CircleCiApi {
    @GET("me")
    suspend fun getUser(): User

    @GET("projects")
    suspend fun getListOfProjects(): List<Project>
}