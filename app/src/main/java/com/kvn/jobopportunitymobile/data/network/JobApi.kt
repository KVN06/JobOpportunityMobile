package com.kvn.jobopportunitymobile.data.network

import com.kvn.jobopportunitymobile.data.model.Job
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JobApi {
    @GET("jobs")
    suspend fun getJobs(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<Job>

    @GET("jobs/{id}")
    suspend fun getJobById(@Path("id") id: String): Job

    @GET("jobs/search")
    suspend fun searchJobs(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<Job>
}
