package com.kvn.jobopportunitymobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kvn.jobopportunitymobile.presentation.navigation.Screen
import com.kvn.jobopportunitymobile.presentation.screens.HomeScreen
import com.kvn.jobopportunitymobile.presentation.screens.JobDetailScreen
import com.kvn.jobopportunitymobile.presentation.screens.SearchScreen
import com.kvn.jobopportunitymobile.ui.theme.JobOpportunityMobileTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobOpportunityMobileTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToJobDetail = { jobId ->
                    navController.navigate(Screen.JobDetail.createRoute(jobId))
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToJobDetail = { jobId ->
                    navController.navigate(Screen.JobDetail.createRoute(jobId))
                }
            )
        }

        composable(
            route = Screen.JobDetail.route,
            arguments = listOf(
                navArgument("jobId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            JobDetailScreen(
                jobId = backStackEntry.arguments?.getString("jobId") ?: "",
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

