package com.kvn.jobopportunitymobile.presentation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object JobDetail : Screen("job_detail/{jobId}") {
        fun createRoute(jobId: String) = "job_detail/$jobId"
    }
    object Profile : Screen("profile")
}
