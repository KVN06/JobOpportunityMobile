package com.kvn.jobopportunitymobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kvn.jobopportunitymobile.data.model.Job
import com.kvn.jobopportunitymobile.data.repository.JobRepository
import com.kvn.jobopportunitymobile.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: JobRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Job>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<Job>>> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotEmpty()) {
            searchJobs()
        } else {
            _uiState.value = UiState.Success(emptyList())
        }
    }

    fun searchJobs(query: String = _searchQuery.value) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.searchJobs(query)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
                }
                .collect { jobs ->
                    _uiState.value = UiState.Success(jobs)
                }
        }
    }
}
