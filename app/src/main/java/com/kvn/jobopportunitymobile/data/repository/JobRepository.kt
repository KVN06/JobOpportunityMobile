package com.kvn.jobopportunitymobile.data.repository

import com.kvn.jobopportunitymobile.data.model.Job
import com.kvn.jobopportunitymobile.data.network.JobApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class JobRepository(private val api: JobApi) {
    fun getJobs(page: Int = 1, limit: Int = 20): Flow<List<Job>> = flow {
        val jobs = api.getJobs(page, limit)
        emit(jobs)
    }.flowOn(Dispatchers.IO)

    fun getJobById(id: String): Flow<Job> = flow {
        val job = api.getJobById(id)
        emit(job)
    }.flowOn(Dispatchers.IO)

    fun searchJobs(query: String, page: Int = 1, limit: Int = 20): Flow<List<Job>> = flow {
        val jobs = api.searchJobs(query, page, limit)
        emit(jobs)
    }.flowOn(Dispatchers.IO)
}
