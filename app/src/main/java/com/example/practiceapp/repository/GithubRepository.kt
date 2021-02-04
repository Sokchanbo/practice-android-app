package com.example.practiceapp.repository

import com.example.practiceapp.extension.request
import com.example.practiceapp.network.api.GithubAPI
import com.example.practiceapp.network.response.ApiResponse
import com.example.practiceapp.network.response.JobResponse
import com.example.practiceapp.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
@OpenForTesting
class GithubRepository @Inject constructor(private val githubAPI: GithubAPI) {

    fun searchJobByTitle(title: String, page: Int): Flow<ApiResponse<List<JobResponse>>> =
        request { githubAPI.searchJobByTitle(title, page) }
}
