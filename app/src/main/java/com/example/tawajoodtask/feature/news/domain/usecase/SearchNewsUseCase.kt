package com.example.tawajoodtask.feature.news.domain.usecase

import com.example.tawajoodtask.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(query: String) =
        repository.searchNews(query)

}