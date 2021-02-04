package com.example.practiceapp.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.practiceapp.features.job.GithubJobSearchViewModel
import com.example.practiceapp.network.response.ApiResponse
import com.example.practiceapp.network.response.JobResponse
import com.example.practiceapp.repository.GithubRepository
import com.example.practiceapp.utils.MainCoroutineRule
import com.example.practiceapp.utils.TestUtils
import com.example.practiceapp.utils.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions

@RunWith(JUnit4::class)
class GithubJobSearchViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val githubRepository = mock(GithubRepository::class.java)
    private lateinit var viewModel: GithubJobSearchViewModel

    @Before
    fun init() {
        viewModel = GithubJobSearchViewModel(githubRepository)
    }

    @Test
    fun empty() {
        val observer = mock<Observer<ApiResponse<List<JobResponse>>>>()
        viewModel.searchJobLiveData.observeForever(observer)
        verifyZeroInteractions(githubRepository)
    }

    @Test
    fun `test search loading`() {
        val observer = mock<Observer<ApiResponse<List<JobResponse>>>>()
        `when`(githubRepository.searchJobByTitle("android", 1))
            .thenReturn(flow { emit(ApiResponse.Loading) })
        viewModel.searchJobLiveData.observeForever(observer)
        viewModel.setJobTitle("android")
        verify(githubRepository).searchJobByTitle("android", 1)
        verify(observer).onChanged(ApiResponse.Loading)
    }

    @Test
    fun `test search success with result`() {
        val jobs = listOf(TestUtils.createJob())
        `when`(githubRepository.searchJobByTitle("android", 1)).thenReturn(flow {
            emit(
                ApiResponse.Success(
                    jobs
                )
            )
        })
        val observer = mock<Observer<ApiResponse<List<JobResponse>>>>()
        viewModel.searchJobLiveData.observeForever(observer)
        viewModel.setJobTitle("android")
        verify(githubRepository).searchJobByTitle("android", 1)
        verify(observer).onChanged(ApiResponse.Success(jobs))
    }
}
