package com.example.tawajoodtask.feature.news.domain.repository

import com.example.tawajoodtask.feature.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(category: String): Flow<List<Article>>

    suspend fun refreshNews(category: String)

    suspend fun searchNews(query: String): List<Article>

    suspend fun getArticleByUrl(url: String): Article?
}