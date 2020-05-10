package com.jonduran.circleci.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CircleCiV2Api {
    @GET("me/collaborations")
    suspend fun getCollaborations()

    @GET("project/{slug}/pipeline")
    suspend fun getGetAllPipelines(@Path("slug") slug: String)
}