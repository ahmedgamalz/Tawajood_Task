package com.example.tawajoodtask.feature.news.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.tawajoodtask.feature.news.domain.usecase.GetArticleByUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getArticleByUrlUseCase: GetArticleByUrlUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    private var loadJob: Job? = null

    private val articleUrl: String? by lazy {
        savedStateHandle.get<String>("url")?.let { Uri.decode(it) }
    }

    init {
        loadArticle()
    }

    fun refresh() {
        loadArticle()
    }

    private fun loadArticle() {
        loadJob?.cancel()
        val url = articleUrl
        if (url != null) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            loadJob = viewModelScope.launch {
                try {
                    // Set a timeout of 5 seconds for local article fetching
                    val article = withTimeoutOrNull(5000) {
                        getArticleByUrlUseCase(url)
                    }
                    _uiState.value = DetailsUiState(
                        isLoading = false,
                        article = article
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        } else {
            _uiState.value = DetailsUiState(
                isLoading = false,
                article = null
            )
        }
    }
}