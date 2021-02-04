package com.example.practiceapp.network.response

sealed class ApiResponse<out T : Any> {

    object Loading : ApiResponse<Nothing>()

    data class Success<out T : Any>(val data: T) : ApiResponse<T>()

    data class Failed(val error: ResponseFailed) : ApiResponse<Nothing>()
}
