package com.example.practiceapp.extension

import com.example.practiceapp.network.response.ApiResponse
import com.example.practiceapp.network.response.ResponseFailed
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlin.coroutines.resume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.ResponseBody
import retrofit2.Response

fun Any.toJsonString(): String = Gson().toJson(this)

fun <T> String.fromJson(classOf: Class<T>): T =
    Gson().fromJson(this, classOf)

fun <T : Any> request(
    apiCall: suspend () -> Response<T>
): Flow<ApiResponse<T>> = flow {
    try {
        emit(ApiResponse.Loading)

        val response = apiCall.invoke()
        if (response.isSuccessful) {
            emit(ApiResponse.Success(response.body()!!))
        } else {
            try {
                emit(
                    ApiResponse.Failed(
                        mappingApiError(response.errorBody())
                    )
                )
            } catch (e: Exception) {
                Logger.e(e, "")
                emit(ApiResponse.Failed(ResponseFailed()))
            }
        }
    } catch (e: Exception) {
        Logger.e(e, "")
        emit(ApiResponse.Failed(ResponseFailed()))
    }
}

@Throws(Exception::class)
suspend fun mappingApiError(errorBody: ResponseBody?) =
    suspendCancellableCoroutine<ResponseFailed> {
        it.resume(errorBody?.string()?.fromJson(ResponseFailed::class.java) ?: ResponseFailed())
    }
