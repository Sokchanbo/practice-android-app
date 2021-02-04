package com.example.practiceapp.network.response

import com.google.gson.annotations.SerializedName

data class JobResponse(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("created_at")
    val createdAt: String,
    @field:SerializedName("company")
    val company: String,
    @field:SerializedName("company_url")
    val companyUrl: String,
    @field:SerializedName("location")
    val location: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("how_to_apply")
    val howToApply: String,
    @field:SerializedName("company_logo")
    val companyLogo: String
)
