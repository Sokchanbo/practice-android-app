package com.example.practiceapp.network.response

import com.google.gson.annotations.SerializedName

data class ResponseFailed(
    @field:SerializedName("code")
    val code: Int = 500
)
