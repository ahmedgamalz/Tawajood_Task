package com.example.tawajoodtask.feature.news.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawajoodtask.feature.news.domain.usecase.GetNewsUseCase
import com.example.tawajoodtask.feature.news.domain.usecase.RefreshNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var getNewsJob: Job? = null
    private var refreshJob: Job? = null

    init {
        loadNews("general")
    }

    fun loadNews(category: String) {
        getNewsJob?.cancel()
        refreshJob?.cancel()

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            selectedCategory = category
        )

        getNewsJob = getNewsUseCase(category)
            .onEach { articles ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    articles = articles
                )
            }
            .launchIn(viewModelScope)

        refreshJob = viewModelScope.launch {
            try {
                // Set a network timeout limit of 8 seconds to prevent hanging spinner
                withTimeoutOrNull(8000) {
                    refreshNewsUseCase(category)
                }
            } catch (e: Exception) {
                // Ignore or log error
            } finally {
                // Ensure the loading indicator is dismissed when refresh job finishes
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}