package com.kvn.jobopportunitymobile.data.model

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val description: String,
    val requirements: List<String>,
    val salary: String?,
    val type: String,
    val postedDate: String,
    val applicationDeadline: String?
)
