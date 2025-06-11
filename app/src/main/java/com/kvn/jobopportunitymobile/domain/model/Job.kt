package com.kvn.jobopportunitymobile.domain.model

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val description: String,
    val type: String,
    val postedDate: String,
    val salary: String? = null
)
