package com.example.tawajoodtask.feature.news.domain.usecase

import com.example.tawajoodtask.feature.news.domain.model.Article
import com.example.tawajoodtask.feature.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticleByUrlUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(
        url: String
    ): Article? {

        return repository.getArticleByUrl(url)
    }
}