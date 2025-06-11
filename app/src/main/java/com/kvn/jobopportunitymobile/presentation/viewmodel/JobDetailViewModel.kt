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

class JobDetailViewModel(
    private val repository: JobRepository,
    private val jobId: String
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Job>>(UiState.Loading)
    val uiState: StateFlow<UiState<Job>> = _uiState
    
    fun loadJob() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getJobById(jobId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
                }
                .collect { job ->
                    _uiState.value = UiState.Success(job)
                }
        }
    }
}
