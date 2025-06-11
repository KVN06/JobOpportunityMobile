package com.kvn.jobopportunitymobile.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kvn.jobopportunitymobile.data.model.Job
import com.kvn.jobopportunitymobile.presentation.components.*
import com.kvn.jobopportunitymobile.presentation.state.UiState
import com.kvn.jobopportunitymobile.presentation.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToJobDetail: (String) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Jobs") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    onSearch = { viewModel.searchJobs(searchQuery) },
                    modifier = Modifier.padding(16.dp)
                )

                when (val state = uiState) {
                    is UiState.Loading -> {
                        LoadingView(
                            modifier = Modifier.weight(1f)
                        )
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = state.message,
                            onRetry = { viewModel.searchJobs(searchQuery) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    is UiState.Success -> {
                        if (state.data.isEmpty() && searchQuery.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No results found",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.data) { job ->
                                    JobCard(
                                        job = job,
                                        onClick = { onNavigateToJobDetail(job.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
