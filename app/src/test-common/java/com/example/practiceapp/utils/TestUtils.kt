package com.example.practiceapp.utils

import com.example.practiceapp.network.response.JobResponse

object TestUtils {

    fun createJob(title: String = "Software Engineer"): JobResponse =
        JobResponse(
            "7cbcbf10-f5ed-4f80-b2dd-7e565c9dd9af",
            "Full Time",
            "https://jobs.github.com/positions/7cbcbf10-f5ed-4f80-b2dd-7e565c9dd9af",
            "Fri Jan 29 01:06:58 UTC 2021",
            "Secureframe",
            "https://secureframe.com",
            "San Francisco or Remote",
            title,
            "Some Description",
            "How to apply",
            "https://jobs.github.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsi" +
                    "bWVzc2FnZSI6IkJBaHBBaUtYIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--7a18686" +
                    "a2034a2533cc659327eecb506fa696b46/netEffects-logo.png"
        )

    fun createJobs(count: Int): List<JobResponse> {
        val list = mutableListOf<JobResponse>()
        repeat(count) {
            list.add(createJob())
        }
        return list
    }
}
