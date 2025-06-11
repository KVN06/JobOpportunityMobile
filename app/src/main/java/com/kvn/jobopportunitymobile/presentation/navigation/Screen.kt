package com.kvn.jobopportunitymobile.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object JobDetail : Screen("job/{jobId}") {
        fun createRoute(jobId: String) = "job/$jobId"
    }
}
