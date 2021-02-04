package com.example.practiceapp.network.api

import com.example.practiceapp.network.response.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("positions.json")
    suspend fun searchJobByTitle(
        @Query("description") title: String,
        @Query("page") page: Int
    ): Response<List<JobResponse>>
}
