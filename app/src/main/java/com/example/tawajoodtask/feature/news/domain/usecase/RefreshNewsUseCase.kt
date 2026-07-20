package com.example.tawajoodtask.feature.news.domain.usecase

import com.example.tawajoodtask.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class RefreshNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(category: String) {
        repository.refreshNews(category)
    }
}