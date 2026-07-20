package com.example.tawajoodtask.feature.news.domain.usecase

import com.example.tawajoodtask.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    operator fun invoke(category: String) =
        repository.getNews(category)

}