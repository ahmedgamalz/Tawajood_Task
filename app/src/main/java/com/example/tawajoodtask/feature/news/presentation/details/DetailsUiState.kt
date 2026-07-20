package com.example.tawajoodtask.feature.news.presentation.details

import com.example.tawajoodtask.feature.news.domain.model.Article

data class DetailsUiState(
    val isLoading: Boolean = true,
    val article: Article? = null,
    val error: String? = null
)