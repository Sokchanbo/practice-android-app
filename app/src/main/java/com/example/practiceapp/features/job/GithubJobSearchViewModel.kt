package com.example.practiceapp.features.job

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.practiceapp.network.response.ApiResponse
import com.example.practiceapp.network.response.JobResponse
import com.example.practiceapp.repository.GithubRepository
import com.example.practiceapp.testing.OpenForTesting
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@OpenForTesting
class GithubJobSearchViewModel @Inject constructor(
    githubRepository: GithubRepository
) : ViewModel() {

    private var _jobs = mutableListOf<JobResponse>()

    private val _currentPage = MutableLiveData<Int>()
    private var _nextPage = 1
    private var _isLastPage = false

    private val _jobTitle = MutableLiveData<String>()
    val searchJobLiveData: LiveData<ApiResponse<List<JobResponse>>> = _currentPage.switchMap {
        githubRepository.searchJobByTitle(_jobTitle.value!!, it).map { response ->
            if (response is ApiResponse.Success) {
                _jobs.addAll(response.data)
                _nextPage += 1
                _isLastPage = response.data.isEmpty()
                return@map ApiResponse.Success(_jobs)
            }
            response
        }.asLiveData()
    }

    fun setJobTitle(title: String) {
        _jobTitle.let {
            if (it.value != title && title.isNotEmpty()) {
                _jobs.clear()
                _jobTitle.value = title
                _nextPage = 1
                _currentPage.value = _nextPage
            }
        }
    }

    fun loadMore() {
        if (_currentPage.value != _nextPage && !_isLastPage) {
            _currentPage.value = _nextPage
        }
    }
}
