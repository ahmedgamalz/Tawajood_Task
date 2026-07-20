package com.example.tawajoodtask.feature.news.presentation.home

import com.example.tawajoodtask.feature.news.domain.model.Article

data class HomeUiState(

    val isLoading: Boolean = false,

    val articles: List<Article> = emptyList(),

    val selectedCategory: String = "general",

    val error: String? = null

)